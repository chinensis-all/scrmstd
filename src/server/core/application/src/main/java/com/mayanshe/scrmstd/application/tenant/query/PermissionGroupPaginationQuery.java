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
 * MERCHANTABILITY_OR_FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.mayanshe.scrmstd.application.tenant.query;

import com.mayanshe.scrmstd.application.tenant.query.dto.PermissionGroupDto;
import com.mayanshe.scrmstd.shared.contract.Query;
import com.mayanshe.scrmstd.shared.model.Pagination;

/**
 * PermissionGroupPaginationQuery: 权限组分页查询
 */
public record PermissionGroupPaginationQuery(
        int page,
        int pageSize
) implements Query<Pagination<PermissionGroupDto>> {
}
