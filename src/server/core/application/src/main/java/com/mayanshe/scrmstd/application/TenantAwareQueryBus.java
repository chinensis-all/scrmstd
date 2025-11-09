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

import com.mayanshe.scrmstd.tenant.configuration.model.TenantDispatcher;
import com.mayanshe.scrmstd.tenant.configuration.repo.TenantDispatcherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component("tenantAwareQueryBus")
@Primary
public class TenantAwareQueryBus implements QueryBus {
    private final ApplicationContext applicationContext;

    private final TenantDispatcherRepository repository;

    // 缓存租户专属 Handler Map
    private final Map<Long, Map<Class<?>, QueryHandler<?, ?>>> tenantHandlers = new ConcurrentHashMap<>();

    // 缓存全局Query Handler Map
    private final Map<Class<?>, QueryHandler<?, ?>> handlers = new ConcurrentHashMap<>();

    @Autowired
    public TenantAwareQueryBus(ApplicationContext applicationContext, TenantDispatcherRepository repository, List<QueryHandler<?, ?>> handlers) {
        this.applicationContext = applicationContext;
        this.repository = repository;

        // 将注入的 Handler 列表转换为 Map，基于泛型获取 Query 类型
        for (QueryHandler<?, ?> handler : handlers) {
            Class<?> QueryType = GenericTypeResolver.resolveQueryType(handler);
            this.handlers.put(QueryType, handler);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> R execute(Query<R> Query) {
        loadHandlersFromDatabase(1L);
        Map<Class<?>, QueryHandler<?, ?>> tenantHandlerMap = tenantHandlers.get(1L);
        QueryHandler<Query<R>, R> handler = (QueryHandler<Query<R>, R>) tenantHandlerMap.get(Query.getClass());
        handler = handler == null ? (QueryHandler<Query<R>, R>) handlers.get(Query.getClass()) : handler;
        if (handler == null) {
            throw new RuntimeException("No QueryHandler found for Query type: " + Query.getClass());
        }

        return handler.handle(Query);
    }

    /**
     * 加载指定租户的 Handler
     */
    private void loadHandlersFromDatabase(Long tenantId) {
        // 如果已经加载过，直接返回
        if (tenantHandlers.containsKey(tenantId)) {
            return;
        }

        List<TenantDispatcher> entities = repository.loadList(tenantId, "Query");
        if (entities.isEmpty()) {
            return;
        }

        Map<Class<?>, QueryHandler<?, ?>> map = new HashMap<>();
        for (TenantDispatcher entity : entities) {
            try {
                Class<?> QueryType = Class.forName(entity.getEntityClass());
                QueryHandler<?, ?> handler = (QueryHandler<?, ?>) applicationContext.getBean(entity.getDispatcherClass());
                map.put(QueryType, handler);
            } catch (Exception ignore) {
            } // 忽略加载失败的 Handler
        }

        tenantHandlers.put(tenantId, map);
    }

    /**
     * 注册租户专属 Handler
     */
    public void removeSpecialTenantHandler(Long tenantId) {
        tenantHandlers.remove(tenantId);
    }

    /**
     * 移除所有租户专属 Handler
     */
    public void removeAllTenantHandler() {
        tenantHandlers.clear();
    }
}
