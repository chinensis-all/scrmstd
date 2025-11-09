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

import com.mayanshe.scrmstd.infrastructure.persistence.mapper.EventMapper;
import com.mayanshe.scrmstd.infrastructure.persistence.po.EventPo;
import com.mayanshe.scrmstd.shared.annotation.SaveDomainEvents;
import com.mayanshe.scrmstd.shared.base.Aggregate;
import com.mayanshe.scrmstd.shared.base.DomainEvent;
import com.mayanshe.scrmstd.shared.model.UserDetail;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class SaveDomainEventsAspect {
    private final EventMapper mapper;

    private final UserDetail userDetail;

    public SaveDomainEventsAspect(EventMapper mapper, UserDetail userDetail) {
        this.mapper = mapper;
        this.userDetail = userDetail;
    }

    /**
     * 保存领域事件
     *
     * @param joinPoint        切点
     * @param saveDomainEvents 注解参数
     */
    @After("@annotation(saveDomainEvents)")
    public void invokeSaveEvents(JoinPoint joinPoint, SaveDomainEvents saveDomainEvents) {
        // 判断方法第一参数是否继承自Aggregate
        Object[] args = joinPoint.getArgs();
        if (args.length == 0 || !(args[0] instanceof Aggregate aggregate)) {
            return;
        }

        // 判断聚合根是否有事件
        List<DomainEvent> events = aggregate.getEvents();
        if (events == null || events.isEmpty()) {
            return;
        }

        // 获取聚合根ID, 如果是采用mysql自增ID, 需要在插入后将获取的ID设置到聚合根中
        long aggregateId = aggregate.getId().id();

        // 保存事件
        for (DomainEvent event : events) {
            Long refId = event.getRefId();
            refId = refId == null ? aggregateId : refId;

            EventPo eventPo = EventPo.builder()
                    .refId(refId)
                    .eventType(event.getEventType())
                    .eventId(event.getEventId())
                    .eventData(event.toJsonString())
                    .occurredAt(event.getOccurredAt())
                    .createdBy(userDetail.toJsonString())
                    .state((short) 0)
                    .build();
            mapper.insert(eventPo);
        }
    }

}
