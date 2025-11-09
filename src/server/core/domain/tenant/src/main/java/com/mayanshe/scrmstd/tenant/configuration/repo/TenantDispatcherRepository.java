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
package com.mayanshe.scrmstd.tenant.configuration.repo;

import com.mayanshe.scrmstd.shared.contract.DomainRepository;
import com.mayanshe.scrmstd.tenant.configuration.model.TenantDispatcher;

import java.util.List;
import java.util.Set;

/**
 * TenantDispatcherRepository: 租户分发器仓储接口
 */
public interface TenantDispatcherRepository extends DomainRepository<TenantDispatcher, Long> {
    List<TenantDispatcher> loadList(Long tenantId, String dispatcherTyper);
}
