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

import com.mayanshe.scrmstd.infrastructure.external.converter.AdminConverter;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.AdminMapper;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.AdminRoleMapper;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.RoleMapper;
import com.mayanshe.scrmstd.infrastructure.persistence.po.AdminPo;
import com.mayanshe.scrmstd.infrastructure.persistence.po.RolePo;
import com.mayanshe.scrmstd.shared.annotation.SaveDomainEvents;
import com.mayanshe.scrmstd.shared.exception.InternalServerException;
import com.mayanshe.scrmstd.shared.exception.RequestConflictException;
import com.mayanshe.scrmstd.tenant.identity.model.Admin;
import com.mayanshe.scrmstd.tenant.identity.repo.AdminRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * AdminRepositoryImpl： 管理员仓库实现类
 */
@Repository
public class AdminRepositoryImpl implements AdminRepository {
    private final AdminMapper mapper;

    private final RoleMapper roleMapper;

    private final AdminRoleMapper adminRoleMapper;

    public AdminRepositoryImpl(AdminMapper mapper, @Lazy RoleMapper roleMapper, @Lazy AdminRoleMapper adminRoleMapper) {
        this.mapper = mapper;
        this.roleMapper = roleMapper;
        this.adminRoleMapper = adminRoleMapper;
    }

    @Override
    public Optional<Admin> load(Long id) {
        AdminPo po = getPo(id);
        return Optional.ofNullable(po == null ? null : AdminConverter.INSTANCE.toAggregate(po));
    }

    @Override
    @Transactional
    @SaveDomainEvents
    public void save(Admin aggregate) {
        // 删除
        if (aggregate.getDeleted()) {
            if (mapper.delete(aggregate.getId().id()) <= 0) {
                throw new InternalServerException("删除管理员失败，管理员ID：" + aggregate.getId());
            }
            return;
        }

        verifyPhoneConflict(aggregate);
        verifyEmailConflict(aggregate);
        verifyUsernameConflict(aggregate);

        AdminPo po = AdminConverter.INSTANCE.toPo(aggregate);

        // 保存角色关联
        saveRoles(aggregate);

        if (aggregate.getId().newed()) {
            if (mapper.insert(po) <= 0) {
                throw new InternalServerException("新增管理员失败，管理员用户名：" + aggregate.getUsername());
            }
            return;
        }

        if (mapper.update(po) <= 0) {
            throw new InternalServerException("更新管理员失败，管理员ID：" + aggregate.getId());
        }
    }

    private void saveRoles(Admin aggregate) {
        // 删除旧角色关联
        adminRoleMapper.deleteByAdminId(aggregate.getId().id());

        if (aggregate.getRoleIds() != null && !aggregate.getRoleIds().isEmpty()) {
            List<RolePo> roles = roleMapper.findByIds(aggregate.getRoleIds());
            if (roles.size() != aggregate.getRoleIds().size()) {
                throw new RequestConflictException("部分角色不存在，角色ID列表：" + aggregate.getRoleIds());
            }

            // 添加新角色关联
            if (adminRoleMapper.batchInsert(aggregate.getId().id(), aggregate.getRoleIds()) <= 0) {
                throw new InternalServerException("保存管理员角色关联失败，管理员ID：" + aggregate.getId());
            }
        }
    }

    /**
     * 验证用户名冲突
     *
     * @param aggregate 管理员聚合根
     */
    private void verifyUsernameConflict(Admin aggregate) {
        Long usernameExistId = mapper.findIdByCondition(Map.of("username", aggregate.getUsername()));
        if (usernameExistId != null && !usernameExistId.equals(aggregate.getId().id())) {
            throw new RequestConflictException("管理员用户名已存在，用户名：" + aggregate.getUsername());
        }
    }

    /**
     * 验证手机号冲突
     *
     * @param aggregate 管理员聚合根
     */
    private void verifyPhoneConflict(Admin aggregate) {
        Long adminPhoneExistId = mapper.findIdByCondition(Map.of("phone", aggregate.getPhone()));
        if (adminPhoneExistId != null && !adminPhoneExistId.equals(aggregate.getId().id())) {
            throw new RequestConflictException("管理员手机号已存在，手机号：" + aggregate.getPhone());
        }
    }

    /**
     * 验证邮箱冲突
     *
     * @param aggregate 管理员聚合根
     */
    private void verifyEmailConflict(Admin aggregate) {
        Long adminEmailExistId = mapper.findIdByCondition(Map.of("email", aggregate.getEmail()));
        if (adminEmailExistId != null && !adminEmailExistId.equals(aggregate.getId().id())) {
            throw new RequestConflictException("管理员邮箱已存在，邮箱：" + aggregate.getEmail());
        }
    }

    /**
     * 获取管理员PO
     *
     * @param id 管理员ID
     * @return 管理员PO
     */
    private AdminPo getPo(Long id) {
        if (id == null || id <= 0) {
            return null;
        }

        return mapper.findById(id);
    }
}
