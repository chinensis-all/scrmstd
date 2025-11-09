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

import org.springframework.core.ResolvableType;

/**
 * GenericTypeResolver: Handler泛型类型解析器
 */
public class GenericTypeResolver {
    /**
     * 解析 CommandHandler 的命令类型
     *
     * @param handler CommandHandler 实例
     * @return 命令类型 Class 对象
     */
    public static Class<?> resolveCommandType(CommandHandler<?, ?> handler) {
        ResolvableType type = ResolvableType.forClass(handler.getClass()).as(CommandHandler.class);
        return (Class<?>) type.getGeneric(0).resolve();
    }

    /**
     * 解析 QueryHandler 的查询类型
     *
     * @param handler QueryHandler 实例
     * @return 查询类型 Class 对象
     */
    public static Class<?> resolveQueryType(QueryHandler<?, ?> handler) {
        ResolvableType type = ResolvableType.forClass(handler.getClass()).as(QueryHandler.class);
        return (Class<?>) type.getGeneric(0).resolve();
    }
}
