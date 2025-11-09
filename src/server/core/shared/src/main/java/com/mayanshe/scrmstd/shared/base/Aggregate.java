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
package com.mayanshe.scrmstd.shared.base;

import com.mayanshe.scrmstd.shared.model.AggregateId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Aggregate: 聚合根基类
 */
public abstract class Aggregate {
    private final List<DomainEvent> events = new ArrayList<>();

    /**
     * 注册领域事件
     *
     * @param event 领域事件
     */
    public void registerEvent(DomainEvent event) {
        if (event != null) {
            events.add(event);
        }
    }

    /**
     * 获取领域事件列表
     *
     * @return 领域事件列表
     */
    public List<DomainEvent> getEvents() {
        return Collections.unmodifiableList(events);
    }

    /**
     * 清空领域事件列表
     */
    public void clearEvents() {
        events.clear();
    }

    /**
     * 获取聚合根ID
     * 保证某些偷懒的功能实现
     *
     * @return 聚合根ID
     */
    abstract public AggregateId getId();
}
