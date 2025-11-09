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
package com.mayanshe.scrmstd.application;

import com.mayanshe.scrmstd.shared.model.Pagination;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * QueryRepository: 通用查询仓储接口
 */
public interface QueryRepository<Dto, OptionDto, Key> {
    Optional<Dto> single(Key key);

    /**
     * 列表查询
     *
     * @param criteria 查询条件
     * @param limit    最大返回数量
     * @return 结果列表
     */
    List<OptionDto> search(Map<String, Object> criteria, long limit);

    /**
     * 分页查询
     *
     * @param criteria 查询条件
     * @param page     页码
     * @param size     每页数量
     * @return 分页结果
     */
    Pagination<Dto> paginate(Map<String, Object> criteria, long page, long size);
}
