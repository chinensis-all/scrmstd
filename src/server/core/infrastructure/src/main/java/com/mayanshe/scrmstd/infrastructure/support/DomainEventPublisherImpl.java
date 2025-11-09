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

import com.mayanshe.scrmstd.application.DomainEventPublisher;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.EventMapper;
import com.mayanshe.scrmstd.infrastructure.persistence.po.EventPo;
import com.mayanshe.scrmstd.shared.base.DomainEvent;
import com.mayanshe.scrmstd.shared.model.UserDetail;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * DomainEventPublisherImpl: 领域事件发布器实现
 */
@Component
public class DomainEventPublisherImpl implements DomainEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    private final EventMapper eventMapper;

    private final UserDetail userDetail;

    public DomainEventPublisherImpl(ApplicationEventPublisher applicationEventPublisher, EventMapper eventMapper, UserDetail userDetail) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.eventMapper = eventMapper;
        this.userDetail = userDetail;
    }

    @Override
    public void publish(DomainEvent event) {
        EventPo eventPo = EventPo.builder()
                .refId(event.getRefId())
                .eventId(event.getEventId())
                .eventType(event.getEventType())
                .eventData(event.toJsonString())
                .occurredAt(event.getOccurredAt())
                .state((short) 0)
                .createdBy(userDetail.toJsonString())
                .createdAt(System.currentTimeMillis()).build();
        if (eventMapper.insert(eventPo) > 0) {
            applicationEventPublisher.publishEvent(event);
        }
    }

    @Override
    public void publish(Iterable<? extends DomainEvent> events) {
        for (DomainEvent event : events) {
            publish(event);
        }
    }

    /**
     * 确认事件已被处理
     *
     * @param event 领域事件
     */
    @Override
    public void confirm(DomainEvent event) {
        EventPo po = eventMapper.findByEventId(event.getEventId());
        if (po != null) {
            applicationEventPublisher.publishEvent(event);
            eventMapper.confirm(po);
        }
    }

    @Override
    public void confirm(Iterable<? extends DomainEvent> events) {
        for (DomainEvent event :  events) {
            confirm(event);
        }
    }
}
