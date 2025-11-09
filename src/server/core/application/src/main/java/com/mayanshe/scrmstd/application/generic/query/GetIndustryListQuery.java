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
package com.mayanshe.scrmstd.application.generic.query;

import com.mayanshe.scrmstd.application.Query;
import com.mayanshe.scrmstd.application.generic.query.dto.IndustryDto;

import java.util.List;

/**
 * GetIndustryListQuery: 获取行业列表查询
 *
 * @param parentId  父类ID
 * @param industryCode 行业编码
 * @param industryName 行业名称
 * @param industryCodes 行业编码列表
 */
public record GetIndustryListQuery(
        Long parentId,
        String industryCode,
        String industryName,
        List<Long> ids,
        List<String> industryCodes
) implements Query<List<IndustryDto>> {}
