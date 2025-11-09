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
package com.mayanshe.scrmstd.infrastructure.support;

import com.mayanshe.scrmstd.infrastructure.persistence.mapper.PaginateMapper;
import com.mayanshe.scrmstd.shared.model.Pagination;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Pager {
    private Pager() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * 自定义分页查询（不带回调）
     *
     * @param mapper    分页Mapper
     * @param page      当前页码
     * @param pageSize  每页大小
     * @param condition 查询条件
     * @param <M>       Mapper类型
     * @param <P>       数据类型
     * @return 分页结果
     */
    public static <M extends PaginateMapper<P>, P> Pagination<P> paginate(M mapper, Map<String, Object> condition, long page, long pageSize) {
        page = Math.max(page, 1);
        pageSize = Math.max(pageSize, 1);

        long total = mapper.count(condition);                                                    // 获取总记录数

        if (total == 0) {
            return new Pagination<P>(new ArrayList<>(), total, page, pageSize);
        }

        long offset = (page - 1) * pageSize;                                                    // 计算偏移量
        condition.put("offset", offset);                                                        // 设置偏移量
        condition.put("limit", pageSize);                                                       // 设置每页大小
        List<P> items = mapper.search(condition);                                               // 分页查询

        return new Pagination<>(items, total, page, pageSize);                                  // 返回分页结果
    }

    /**
     * 自定义分页查询（带回调）
     *
     * @param mapper    分页Mapper
     * @param page      当前页码
     * @param pageSize  每页大小
     * @param condition 查询条件
     * @param callback  回调函数，用于处理每个源对象
     * @param <M>       Mapper类型
     * @param <P>       源数据类型
     * @param <T>       目标数据类型
     * @return 分页结果
     */
    public static <M extends PaginateMapper<P>, P, T> Pagination<T> paginate(M mapper, Map<String, Object> condition, Callback<P, T> callback, long page, long pageSize) {
        page = Math.max(page, 1);
        pageSize = Math.max(pageSize, 1);

        long total = mapper.count(condition);                                                    // 获取总记录数

        if (total == 0) {
            return new Pagination<>(new ArrayList<>(), total, page, pageSize);
        }

        long offset = (page - 1) * pageSize;                                                    // 计算偏移量
        condition.put("offset", offset);                                                        // 设置偏移量
        condition.put("limit", pageSize);                                                       // 设置每页大小
        List<P> sources = mapper.search(condition);                                             // 分页查询
        List<T> items = sources.stream().map(source -> {
            try {
                return callback.handle(source); // 使用回调处理每个源对象
            } catch (Exception e) {
                throw new RuntimeException("Error processing source object", e);
            }
        }).toList();

        return new Pagination<>(items, total, page, pageSize);                                  // 返回分页结果
    }

    @FunctionalInterface
    public interface Callback<P, T> {
        T handle(P source) throws Exception; // 允许抛出受检异常
    }
}
