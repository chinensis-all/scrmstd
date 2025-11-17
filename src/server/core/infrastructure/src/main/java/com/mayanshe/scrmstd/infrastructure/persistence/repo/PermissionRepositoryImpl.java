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

import com.mayanshe.scrmstd.domain.tenant.identity.model.Permission;
import com.mayanshe.scrmstd.domain.tenant.identity.repo.PermissionRepository;
import com.mayanshe.scrmstd.infrastructure.external.converter.PermissionConverter;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.PermissionMapper;
import com.mayanshe.scrmstd.infrastructure.persistence.po.PermissionPo;
import com.mayanshe.scrmstd.shared.annotation.SaveDomainEvents;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import com.mayanshe.scrmstd.shared.exception.InternalServerException;
import com.mayanshe.scrmstd.shared.exception.RequestConflictException;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

/**
 * @author: 张西海
 * @date: 2025-11-16
 * @version: 1.0
 * @description: PermissionRepositoryImpl: 权限仓储实现
 */
@Repository
public class PermissionRepositoryImpl implements PermissionRepository {
    private final PermissionMapper mapper;

    public PermissionRepositoryImpl(PermissionMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public AggregateId nextId() {
        return new AggregateId(IdGenerator.nextId());
    }

    @Override
    public Optional<Permission> load(AggregateId id) {
        PermissionPo po = getPo(id.id());
        return Optional.ofNullable(po == null ? null : PermissionConverter.INSTANCE.toAggregate(po));
    }

    @Override
    @Transactional
    @SaveDomainEvents
    public void save(Permission aggregate) {
        if (aggregate.getDeleted()) {
            if (mapper.delete(aggregate.getId().id()) <= 0) {
                throw new InternalServerException("删除权限失败，权限ID：" + aggregate.getId());
            }
            return;
        }

        verifyConflict(aggregate);

        PermissionPo po = PermissionConverter.INSTANCE.toPo(aggregate);

        if (aggregate.getId().newed()) {
            if (mapper.insert(po) <= 0) {
                throw new InternalServerException("新增权限失败，权限名称：" + aggregate.getPermissionName());
            }
            return;
        }

        if (mapper.update(po) <= 0) {
            throw new InternalServerException("更新权限失败，权限ID：" + aggregate.getId());
        }
    }

    private void verifyConflict(Permission aggregate) {
        Long permissionNameExistId = mapper.findIdByCondition(Map.of("permissionName", aggregate.getPermissionName()));
        if (permissionNameExistId != null && !permissionNameExistId.equals(aggregate.getId().id())) {
            throw new RequestConflictException("权限名称已存在，名称：" + aggregate.getPermissionName());
        }

        Long displayNameExistId = mapper.findIdByCondition(Map.of("displayName", aggregate.getDisplayName()));
        if (displayNameExistId != null && !displayNameExistId.equals(aggregate.getId().id())) {
            throw new RequestConflictException("权限显示名称已存在，显示名称：" + aggregate.getDisplayName());
        }
    }

    private PermissionPo getPo(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        return mapper.findById(id);
    }
}
