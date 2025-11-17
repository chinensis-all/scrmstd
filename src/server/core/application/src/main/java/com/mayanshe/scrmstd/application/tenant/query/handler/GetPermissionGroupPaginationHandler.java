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

import com.mayanshe.scrmstd.application.tenant.query.PermissionGroupPaginationQuery;
import com.mayanshe.scrmstd.application.tenant.query.dto.PermissionGroupDto;
import com.mayanshe.scrmstd.application.tenant.query.repo.PermissionGroupQueryRepository;
import com.mayanshe.scrmstd.shared.contract.QueryHandler;
import com.mayanshe.scrmstd.shared.model.Pagination;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * GetPermissionGroupPaginationHandler: 获取权限组分页查询处理器
 */
@Service
public class GetPermissionGroupPaginationHandler implements QueryHandler<PermissionGroupPaginationQuery, Pagination<PermissionGroupDto>> {
    private final PermissionGroupQueryRepository permissionGroupQueryRepository;

    public GetPermissionGroupPaginationHandler(PermissionGroupQueryRepository permissionGroupQueryRepository) {
        this.permissionGroupQueryRepository = permissionGroupQueryRepository;
    }

    @Override
    public Pagination<PermissionGroupDto> handle(PermissionGroupPaginationQuery query) {
        return permissionGroupQueryRepository.paginate(Collections.emptyMap(), query.page(), query.pageSize());
    }
}
