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

import com.mayanshe.scrmstd.infrastructure.external.converter.PermissionGroupConvert;
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

@Repository
public class PermissionGroupRepositoryImpl implements PermissionGroupRepository {
    private final PermissionGroupMapper mapper;

    public PermissionGroupRepositoryImpl(PermissionGroupMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Optional<PermissionGroup> load(Long id) {
        PermissionGroupPo po = getPo(id);
        return Optional.ofNullable(po == null ? null : PermissionGroupConvert.INSTANCE.toAggregate(po));
    }

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

        verifyGroupNameConflict(aggregate);

        PermissionGroupPo po = PermissionGroupConvert.INSTANCE.toPo(aggregate);

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

    private void verifyGroupNameConflict(PermissionGroup aggregate) {
        Long groupNameExistId = mapper.findIdByCondition(Map.of("groupName", aggregate.getGroupName()));
        if (groupNameExistId != null && !groupNameExistId.equals(aggregate.getId().id())) {
            throw new RequestConflictException("权限组名称已存在，名称：" + aggregate.getGroupName());
        }
    }

    private PermissionGroupPo getPo(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        return mapper.findById(id);
    }
}
