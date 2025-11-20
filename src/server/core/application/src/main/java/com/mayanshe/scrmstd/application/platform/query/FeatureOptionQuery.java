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
package com.mayanshe.scrmstd.application.platform.query;

import com.mayanshe.scrmstd.application.OptionDto;
import com.mayanshe.scrmstd.application.Query;

import java.util.List;

/**
 * FeatureOptionQuery: Saas功能点选项查询
 *
 * @param parentId 父功能点ID
 * @param keywords 搜索关键词
 * @param deleted  是否删除
 */
public record FeatureOptionQuery(
        Long parentId,
        String keywords,
        Boolean deleted
) implements Query<List<OptionDto>> {}
