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
package com.mayanshe.scrmstd.message.log.event;

import com.mayanshe.scrmstd.message.log.type.EmailSendStatus;
import com.mayanshe.scrmstd.message.send.service.EmailSendService;
import com.mayanshe.scrmstd.shared.base.DomainEvent;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * 邮件发送日志已创建事件
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class EmailLogCreatedEvent extends DomainEvent {
    private AggregateId emailLogId;

    private Long tenantId;

    private Long taskId;

    private Long templateId;

    private String sender;

    private String recipient;

    private List<String> cc;

    private String subject;

    private String body;

    private Map<String, Object> params;

    private Map<String, String> attachments;

    private EmailSendStatus status;
}
