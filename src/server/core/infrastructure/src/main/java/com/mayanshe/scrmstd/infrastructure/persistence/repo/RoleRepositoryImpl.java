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

import com.mayanshe.scrmstd.infrastructure.external.converter.RoleConverter;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.RoleMapper;
import com.mayanshe.scrmstd.infrastructure.persistence.po.RolePo;
import com.mayanshe.scrmstd.shared.annotation.SaveDomainEvents;
import com.mayanshe.scrmstd.shared.exception.InternalServerException;
import com.mayanshe.scrmstd.shared.exception.RequestConflictException;
import com.mayanshe.scrmstd.tenant.identity.model.Role;
import com.mayanshe.scrmstd.tenant.identity.repo.RoleRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {
    private final RoleMapper mapper;

    public RoleRepositoryImpl(RoleMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Optional<Role> load(Long id) {
        RolePo po = getPo(id);
        return Optional.ofNullable(po == null ? null : RoleConverter.INSTANCE.toAggregate(po));
    }

    @Override
    @Transactional
    @SaveDomainEvents
    public void save(Role aggregate) {
        // 删除
        if (aggregate.getDeleted()) {
            if (mapper.delete(aggregate.getId().id()) <= 0) {
                throw new InternalServerException("删除角色失败，角色名称：" + aggregate.getRoleName());
            }
            return;
        }

        verifyRoleNameConflict(aggregate);

        RolePo po = RoleConverter.INSTANCE.toPo(aggregate);

        // 新增
        if (aggregate.getId().newed()) {
            if (mapper.insert(po) <= 0) {
                throw new InternalServerException("新增角色失败，角色名称：" + aggregate.getRoleName());
            }
        }

        // 更新
        if (mapper.update(po) <= 0) {
            throw new InternalServerException("更新角色失败，角色名称：" + aggregate.getRoleName());
        }
    }

    /**
     * 验证角色名称冲突
     *
     * @param aggregate 角色聚合根
     */
    private void verifyRoleNameConflict(Role aggregate) {
        Long existingId = mapper.findIdByRoleName(aggregate.getTenantId(), aggregate.getRoleName());
        if (existingId != null && !existingId.equals(aggregate.getId().id())) {
            throw new RequestConflictException("角色名称已存在，角色名称：" + aggregate.getRoleName());
        }
    }

    /**
     * 获取RolePo
     *
     * @param id 角色ID
     * @return 角色持久化对象
     */
    private RolePo getPo(Long id) {
        if (id == null || id <= 0) {
            return null;
        }

        return mapper.findById(id);
    }
}
