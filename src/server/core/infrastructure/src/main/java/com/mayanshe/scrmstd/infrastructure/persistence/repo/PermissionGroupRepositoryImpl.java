/*
 * [ScrmStd] - 通用SCRM系统
 * Copyright (C) [2025] [张西海]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.mayanshe.scrmstd.infrastructure.persistence.repo;

import com.mayanshe.scrmstd.infrastructure.external.converter.PermissionGroupConverter;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.PermissionGroupMapper;
import com.mayanshe.scrmstd.infrastructure.persistence.po.PermissionGroupPo;
import com.mayanshe.scrmstd.shared.annotation.SaveDomainEvents;
import com.mayanshe.scrmstd.shared.exception.InternalServerException;
import com.mayanshe.scrmstd.shared.exception.RequestConflictException;
import com.mayanshe.scrmstd.tenant.identity.model.PermissionGroup;
import com.mayanshe.scrmstd.tenant.identity.repo.PermissionGroupRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

/**
 * PermissionGroupRepositoryImpl: 权限组仓储实现
 */
@Repository
public class PermissionGroupRepositoryImpl implements PermissionGroupRepository {
    private final PermissionGroupMapper mapper;

    public PermissionGroupRepositoryImpl(PermissionGroupMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 加载权限组聚合根
     *
     * @param id 主键
     * @return   权限组聚合根对象
     */
    @Override
    public Optional<PermissionGroup> load(Long id) {
        PermissionGroupPo po = getPo(id);
        return Optional.ofNullable(po == null ? null : PermissionGroupConverter.INSTANCE.toAggregate(po));
    }

    /**
     * 保存权限组聚合根
     *
     * @param aggregate 聚合根对象
     */
    @Override
    @Transactional
    @SaveDomainEvents
    public void save(PermissionGroup aggregate) {
        if (aggregate.getDeleted()) {
            if (mapper.delete(aggregate.getId().id()) <= 0) {
                throw new InternalServerException("删除权限组失败，权限组ID：" + aggregate.getId());
            }
            return;
        }

        verifyConflict(aggregate);

        PermissionGroupPo po = PermissionGroupConverter.INSTANCE.toPo(aggregate);

        if (aggregate.getId().newed()) {
            if (mapper.insert(po) <= 0) {
                throw new InternalServerException("新增权限组失败，权限组名称：" + aggregate.getGroupName());
            }
            return;
        }

        if (mapper.update(po) <= 0) {
            throw new InternalServerException("更新权限组失败，权限组ID：" + aggregate.getId());
        }
    }

    /**
     * 验证权限组冲突
     *
     * @param aggregate 权限组聚合根
     */
    private void verifyConflict(PermissionGroup aggregate) {
        Long groupNameExistId = mapper.findIdByCondition(Map.of("groupName", aggregate.getGroupName()));
        if (groupNameExistId != null && !groupNameExistId.equals(aggregate.getId().id())) {
            throw new RequestConflictException("权限组名称已存在，名称：" + aggregate.getGroupName());
        }

        Long displayNameExistId = mapper.findIdByCondition(Map.of("displayName", aggregate.getDisplayName()));
        if (displayNameExistId != null && !displayNameExistId.equals(aggregate.getId().id())) {
            throw new RequestConflictException("权限组显示名称已存在，显示名称：" + aggregate.getDisplayName());
        }

        if (aggregate.getParentId() > 0) {
            Long parentExistId = mapper.findIdByCondition(Map.of("id", aggregate.getParentId()));
            if (parentExistId == null) {
                throw new RequestConflictException("父权限组不存在，父权限组ID：" + aggregate.getParentId());
            }
        }
    }

    /**
     * 获取权限组PO
     *
     * @param id 权限组ID
     * @return   权限组PO
     */
    private PermissionGroupPo getPo(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        return mapper.findById(id);
    }
}
