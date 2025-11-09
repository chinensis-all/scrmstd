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

import com.mayanshe.scrmstd.infrastructure.external.converter.TenantConverter;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.TenantMapper;
import com.mayanshe.scrmstd.infrastructure.persistence.po.TenantPo;
import com.mayanshe.scrmstd.shared.annotation.SaveDomainEvents;
import com.mayanshe.scrmstd.shared.exception.InternalServerException;
import com.mayanshe.scrmstd.shared.exception.RequestConflictException;
import com.mayanshe.scrmstd.tenant.management.model.Tenant;
import com.mayanshe.scrmstd.tenant.management.repo.TenantRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Repository
public class TenantRepositoryImpl implements TenantRepository {
    private final TenantMapper mapper;

    public TenantRepositoryImpl(TenantMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Optional<Tenant> load(Long id) {
        TenantPo po = getPo(id);
        return Optional.ofNullable(po == null ? null : TenantConverter.INSTANCE.toAggregate(po));
    }

    @Override
    @Transactional
    @SaveDomainEvents
    public void save(Tenant aggregate) {
        if (aggregate.getSaves().contains("base")) {
            saveBase(aggregate);
        }
    }

    /**
     * 保存租户基础信息
     *
     * @param aggregate 租户聚合根
     */
    public void saveBase(Tenant aggregate) {
        if (aggregate.getDeleted()) {
            if (mapper.delete(aggregate.getId().id()) <= 0) {
                throw new InternalServerException("删除租户失败，租户ID：" + aggregate.getId().id());
            }
            return;
        }

        verifyBase(aggregate);
        TenantPo po = TenantConverter.INSTANCE.toPo(aggregate);

        if (aggregate.getId().newed()) {
            if (mapper.insert(po) <= 0) {
                throw new InternalServerException("新增租户失败，租户名称：" + aggregate.getTenantName());
            }
            return;
        }

        if (mapper.update(po) <= 0) {
            throw new InternalServerException("更新租户信息失败，租户ID：" + aggregate.getId().id());
        }
    }

    /**
     * 验证租户基础信息
     *
     * @param aggregate 租户聚合根
     */
    private void verifyBase(Tenant aggregate) {
        Long tenantNameExistId = mapper.findIdByTenantName(aggregate.getTenantName());
        if (tenantNameExistId != null && !tenantNameExistId.equals(aggregate.getId().id())) {
            throw new RequestConflictException("租户名称已存在，租户名称：" + aggregate.getTenantName());
        }

        Long creditCodeExistId = mapper.findIdByCreditCode(aggregate.getCreditCode());
        if (creditCodeExistId != null && !creditCodeExistId.equals(aggregate.getId().id())) {
            throw new RequestConflictException("社会信用代码已存在，社会信用代码：" + aggregate.getCreditCode());
        }

        Long industryCodeExitId = mapper.findIdByIndustryCode(aggregate.getIndustryCode());
        if (industryCodeExitId != null && !industryCodeExitId.equals(aggregate.getId().id())) {
            throw new RequestConflictException("行业代码已存在，行业代码：" + aggregate.getIndustryCode());
        }
    }

    /**
     * 获取TenantPo
     *
     * @param id 租户ID
     * @return TenantPo
     */
    private TenantPo getPo(Long id) {
        if (id == null || 0 >= id) {
            return null;
        }

        return mapper.findById(id);
    }
}
