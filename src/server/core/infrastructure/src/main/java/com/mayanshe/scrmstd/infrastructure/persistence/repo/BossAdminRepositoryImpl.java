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

import com.mayanshe.scrmstd.domain.boss.admin.model.BossAdmin;
import com.mayanshe.scrmstd.domain.boss.admin.repo.BossAdminRepository;
import com.mayanshe.scrmstd.infrastructure.external.converter.BossAdminConverter;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.BossAdminMapper;
import com.mayanshe.scrmstd.infrastructure.persistence.po.BossAdminPo;
import com.mayanshe.scrmstd.shared.annotation.SaveDomainEvents;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

/**
 * Boss管理员仓储实现
 */
@Repository
public class BossAdminRepositoryImpl implements BossAdminRepository {
    private final BossAdminMapper mapper;

    private final BossAdminConverter converter;

    public BossAdminRepositoryImpl(BossAdminMapper mapper,@Lazy BossAdminConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }

    @Override
    public Optional<BossAdmin> loadByAccount(String account) {
        BossAdminPo po;
        if (account.contains("@")) {
            po = mapper.findByEmail(account);
        } else if (account.matches("^1[3-9]\\d{9}$")) {
            po = mapper.findByPhone(account);
        } else {
            po = mapper.findByUsername(account);
        }

        return Optional.ofNullable(converter.toAggregate(po));
    }

    @Override
    public Optional<BossAdmin> load(Long id) {
        BossAdminPo po = this.getPo(id);
        if (po == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(converter.toAggregate(po));
    }

    @Override
    @Transactional
    @SaveDomainEvents
    public void save(BossAdmin aggregate) {
        // 删除
        if (aggregate.isDeleted()) {
            if (mapper.delete(aggregate.getId().id()) <= 0) {
                throw new RuntimeException("禁用Boss管理员失败，ID=" + aggregate.getId().id());
            }
            return;
        }

        verifyExistence(aggregate);
        BossAdminPo po = converter.toPo(aggregate);

        // 新增
        if (aggregate.getId().newed()) {
            if (mapper.insert(po) <= 0) {
                throw new RuntimeException("新增Boss管理员失败，" + aggregate);
            }
            return;
        }

        // 修改
        if (mapper.update(po) <= 0) {
            throw new RuntimeException("修改Boss管理员失败，" + aggregate);
        }
    }

    /**
     * 验证唯一性
     *
     * @param aggregate Boss管理员聚合根
     */
    private void verifyExistence(BossAdmin aggregate) {
        Long existingUsernameId = mapper.findIdBy(Map.of("username", aggregate.getUsername()));
        if (existingUsernameId != null && !existingUsernameId.equals(aggregate.getId().id())) {
            throw new RuntimeException("Boss管理员用户名已存在，username=" + aggregate.getUsername());
        }

        Long existingPhoneId = mapper.findIdBy(Map.of("phone", aggregate.getPhone()));
        if (existingPhoneId != null && !existingPhoneId.equals(aggregate.getId().id())) {
            throw new RuntimeException("Boss管理员手机号已存在，phone=" + aggregate.getPhone());
        }

        Long existingEmailId = mapper.findIdBy(Map.of("email", aggregate.getEmail()));
        if (existingEmailId != null && !existingEmailId.equals(aggregate.getId().id())) {
            throw new RuntimeException("Boss管理员邮箱已存在，email=" + aggregate.getEmail());
        }
    }


    /**
     * 根据ID获取Boss管理员
     *
     * @param id Boss管理员ID
     * @return Boss管理员PO
     */
    private BossAdminPo getPo(Long id) {
        if (id == null || id <= 0) {
            return null;
        }

        return mapper.findById(id);
    }
}
