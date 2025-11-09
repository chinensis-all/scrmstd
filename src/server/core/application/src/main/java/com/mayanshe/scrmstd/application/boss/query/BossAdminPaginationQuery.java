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
package com.mayanshe.scrmstd.application.boss.query;

import com.mayanshe.scrmstd.application.Query;
import com.mayanshe.scrmstd.application.boss.query.dto.BossAdminDto;
import com.mayanshe.scrmstd.shared.model.Pagination;

/**
 * 类型 : BossAdminPaginationQuery
 *
 * @param keywords 关键词
 * @param deleted  是否删除
 * @param page     页码
 * @param pageSize 每页大小
 */
public record BossAdminPaginationQuery(
        String keywords,
        Boolean deleted,
        Long page,
        Long pageSize
) implements Query<Pagination<BossAdminDto>> {
    @Override
    public Long page() {
        return page == null || page <= 0 ? 1 : page;
    }

    @Override
    public Long pageSize() {
        return pageSize == null || pageSize <= 0 ? 15 : pageSize;
    }
}
