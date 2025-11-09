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
package com.mayanshe.scrmstd.application.message.query;

import com.mayanshe.scrmstd.application.Query;
import com.mayanshe.scrmstd.application.message.query.dto.EmailTemplateDto;
import com.mayanshe.scrmstd.shared.model.Pagination;

/**
 * 邮件模板分页查询
 *
 * @param keywords 关键词
 * @param category 类别
 * @param page     页码
 * @param pageSize 每页大小
 */
public record EmailTemplatePaginationQuery(
        String keywords,
        String category,
        Long page,
        Long pageSize
) implements Query<Pagination<EmailTemplateDto>> {
    @Override
    public Long page() {
        return page == null || page <= 0 ? 1 : page;
    }

    @Override
    public Long pageSize() {
        return pageSize == null || pageSize <= 0 ? 15 : pageSize;
    }
}
