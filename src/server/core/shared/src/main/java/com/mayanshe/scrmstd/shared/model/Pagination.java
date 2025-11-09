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
package com.mayanshe.scrmstd.shared.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 分页结果
 *
 * @param items    当前页数据
 * @param total    总记录数
 * @param page     当前页码
 * @param pageSize 每页大小
 * @param <T>      数据类型
 */
public record Pagination<T>(List<T> items, long total, long page, long pageSize) {
    public List<T> items() {
        return items.isEmpty() ? List.of() : items;
    }

    @JsonProperty
    public long totalPages() {
        return (total + pageSize - 1) / pageSize;
    }

    @JsonProperty
    public long counts() {
        return items.size();
    }

    @JsonProperty
    public long previousPage() {
        return Math.max(1, page - 1);
    }

    @JsonProperty
    public long nextPage() {
        return Math.min(totalPages(), page + 1);
    }
}
