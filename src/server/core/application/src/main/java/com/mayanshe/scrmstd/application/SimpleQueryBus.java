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

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component("simpleQueryBus")
public class SimpleQueryBus implements QueryBus {
    private final Map<Class<?>, QueryHandler<?, ?>> handlers = new ConcurrentHashMap<>();

    public SimpleQueryBus(List<QueryHandler<?, ?>> handlers) {
        // 将注入的 Handler 列表转换为 Map，基于泛型获取 Query 类型
        for (QueryHandler<?, ?> handler : handlers) {
            Class<?> queryType = GenericTypeResolver.resolveQueryType(handler);
            this.handlers.put(queryType, handler);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> R execute(Query<R> query) {
        QueryHandler<Query<R>, R> handler = (QueryHandler<Query<R>, R>) handlers.get(query.getClass());
        handler = handler != null ? handler : (QueryHandler<Query<R>, R>) handlers.get(Object.class);
        if (handler == null) {
            throw new RuntimeException("No QueryHandler found for query type: " + query.getClass());
        }
        return handler.handle(query);
    }
}
