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
package com.mayanshe.scrmstd.application.tenant.query.handler;

import com.mayanshe.scrmstd.application.OptionDto;
import com.mayanshe.scrmstd.application.tenant.query.PermissionGroupOptionQuery;
import com.mayanshe.scrmstd.application.tenant.query.repo.PermissionGroupQueryRepository;
import com.mayanshe.scrmstd.shared.contract.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * GetPermissionGroupOptionHandler: 获取权限组选项查询处理器
 */
@Service
public class GetPermissionGroupOptionHandler implements QueryHandler<PermissionGroupOptionQuery, List<OptionDto>> {
    private final PermissionGroupQueryRepository permissionGroupQueryRepository;

    public GetPermissionGroupOptionHandler(PermissionGroupQueryRepository permissionGroupQueryRepository) {
        this.permissionGroupQueryRepository = permissionGroupQueryRepository;
    }

    @Override
    public List<OptionDto> handle(PermissionGroupOptionQuery query) {
        return permissionGroupQueryRepository.search(Collections.emptyMap(), 100);
    }
}
