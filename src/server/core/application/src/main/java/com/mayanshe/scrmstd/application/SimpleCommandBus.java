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

/**
 * SimpleCommandBus: 简单命令总线实现
 * 不是默认的 CommandBus 实现， 需要指定名称调用
 */
@Component("simpleCommandBus")
public class SimpleCommandBus implements CommandBus {
    private final Map<Class<?>, CommandHandler<?, ?>> handlers = new ConcurrentHashMap<>();

    public SimpleCommandBus(List<CommandHandler<?, ?>> handlers) {
        // 将注入的 Handler 列表转换为 Map，基于泛型获取 Command 类型
        for (CommandHandler<?, ?> handler : handlers) {
            Class<?> commandType = GenericTypeResolver.resolveCommandType(handler);
            this.handlers.put(commandType, handler);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> R execute(Command<R> command) {
        CommandHandler<Command<R>, R> handler = (CommandHandler<Command<R>, R>) handlers.get(command.getClass());
        handler = handler != null ? handler : (CommandHandler<Command<R>, R>) handlers.get(Object.class);
        if (handler == null) {
            throw new RuntimeException("No CommandHandler found for command type: " + command.getClass());
        }
        return handler.handle(command);
    }
}
