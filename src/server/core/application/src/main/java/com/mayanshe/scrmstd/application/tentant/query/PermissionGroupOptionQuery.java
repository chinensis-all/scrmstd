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
 * GNU Affero General Public License for more details *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.mayanshe.scrmstd.application.tentant.query;

import com.mayanshe.scrmstd.application.OptionDto;
import com.mayanshe.scrmstd.application.Query;

import java.util.List;

/**
 * PermissionGroupOptionLQuery: 查询权限组下拉选项
 *
 * @param parentId 上级权限组ID
 * @param keywords 关键字
 */
public record PermissionGroupOptionQuery(
        Long parentId,
        String keywords
) implements Query<List<OptionDto>> {}
