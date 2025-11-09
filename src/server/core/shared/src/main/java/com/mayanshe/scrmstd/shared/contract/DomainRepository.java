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
package com.mayanshe.scrmstd.shared.contract;

import com.mayanshe.scrmstd.shared.model.Pagination;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Repo: 领域仓储接口，定义基本的加载和保存方法
 * @param <Mo> 聚合根类型
 * @param <Key> 主键类型
 */
public interface DomainRepository<Mo, Key> {
    /** 根据主键加载聚合根
     *
     * @param key 主键
     * @return 聚合根对象的Optional封装
     */
    Optional<Mo> load(Key key);

    /** 保存聚合根及其对应的持久化对象
     *
     * @param mo 聚合根对象
     */
    void save(Mo mo);
}
