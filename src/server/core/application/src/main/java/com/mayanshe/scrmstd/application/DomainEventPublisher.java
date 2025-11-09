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

import com.mayanshe.scrmstd.shared.base.DomainEvent;

/**
 * DomainEventPublisher: 领域事件发布器借口
 */
public interface DomainEventPublisher {
    /** 发布单个领域事件
     *
     * @param event 领域事件
     */
    void publish(DomainEvent event);

    /** 批量发布领域事件
     *
     * @param events 领域事件列表
     */
    void publish(Iterable<? extends DomainEvent> events);

    /**
     * 确认事件已记录
     *
     * @param event 领域事件
     */
    void confirm(DomainEvent event);

    /**
     * 批量确认事件已记录
     *
     * @param events 领域事件列表
     */
    void confirm(Iterable<? extends DomainEvent> events);
}
