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
package com.mayanshe.scrmstd.message.log.model;

import com.mayanshe.scrmstd.message.log.event.EmailLogCreatedEvent;
import com.mayanshe.scrmstd.message.log.event.EventLogModifiedEvent;
import com.mayanshe.scrmstd.message.log.type.EmailSendStatus;
import com.mayanshe.scrmstd.shared.base.Aggregate;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * 邮件发送日志聚合根
 */
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class EmailLog extends Aggregate {
    private AggregateId id;

    @Builder.Default
    private Long tenantId = 0L;

    @Builder.Default
    private Long taskId = 0L;

    @Builder.Default
    private Long templateId = 0L;

    private String sender;

    private String recipient;

    private List<String> cc;

    private String subject;

    private String body;

    private Map<String, Object> params;

    private Map<String, String> attachments;

    private EmailSendStatus status;

    @Builder.Default
    private Long version = 0L;

    /**
     * 创建日志
     */
    public void create() {
        EmailLogCreatedEvent event = EmailLogCreatedEvent.builder()
                .refId(this.getId().id())
                .emailLogId(this.getId())
                .tenantId(this.getTenantId())
                .taskId(this.getTaskId())
                .templateId(this.getTemplateId())
                .sender(this.getSender())
                .recipient(this.getRecipient())
                .cc(this.getCc())
                .subject(this.getSubject())
                .body(this.getBody())
                .params(this.getParams())
                .attachments(this.getAttachments())
                .status(this.getStatus())
                .build();
        this.registerEvent(event);
    }

    /**
     * 修改日志状态
     *
     * @param status 新的状态
     */
    public void modified(String status) {
        this.status = EmailSendStatus.valueOf(status);

        EventLogModifiedEvent event = EventLogModifiedEvent.builder()
                .eventLogId(this.getId())
                .tenantId(this.getTenantId())
                .status(status)
                .build();
        this.registerEvent(event);
    }
}
