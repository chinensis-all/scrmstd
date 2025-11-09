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
package com.mayanshe.scrmstd.application.message.listener;

import com.mayanshe.scrmstd.application.CommandBus;
import com.mayanshe.scrmstd.application.message.command.CreateEmailLogCommand;
import com.mayanshe.scrmstd.application.tentant.listener.TenantCreatedListener;
import com.mayanshe.scrmstd.message.send.event.EmailSendFailedEvent;
import com.mayanshe.scrmstd.message.send.event.EmailSentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class EmailSendEventListener {
    private static final Logger logger = LoggerFactory.getLogger(TenantCreatedListener.class);

    private final CommandBus commandBus;

    public EmailSendEventListener(@Qualifier("simpleCommandBus") CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    /**
     * 监听邮件已发送事件
     *
     * @param event 邮件已发送事件
     */
    @Async
    @EventListener
    public void onEmailSentEvent(EmailSentEvent event) {
        RecordEmailLog(
                event.getTenantId(),
                event.getTaskId(),
                event.getTemplateId(),
                event.getFrom(),
                event.getRecipient(),
                event.getCc(),
                event.getSubject(),
                event.getBody(),
                event.getParams(),
                event.getAttachments(),
                "SENT"
        );
    }

    /**
     * 监听邮件发送失败事件
     *
     * @param event 邮件发送失败事件
     */
    @Async
    @EventListener
    public void onEmailSendFailedEvent(EmailSendFailedEvent event) {
        RecordEmailLog(
                event.getTenantId(),
                event.getTaskId(),
                event.getTemplateId(),
                event.getFrom(),
                event.getRecipient(),
                event.getCc(),
                event.getSubject(),
                event.getBody(),
                event.getParams(),
                event.getAttachments(),
                "FAILED"
        );
    }

    /**
     * 记录邮件发送日志
     *
     * @param tenantId    租户ID
     * @param taskId      任务ID
     * @param templateId  模板ID
     * @param sender      发送者
     * @param recipient   接收者
     * @param cc          抄送者列表
     * @param subject     主题
     * @param body        内容
     * @param params      参数
     * @param attachments 附件
     * @param status      状态
     */
    private void RecordEmailLog(
            Long tenantId,
            Long taskId,
            Long templateId,
            String sender,
            String recipient,
            List<String> cc,
            String subject,
            String body,
            Map<String, Object> params,
            Map<String, String> attachments,
            String status
    ) {
        CreateEmailLogCommand command = new CreateEmailLogCommand(
                tenantId,
                taskId,
                templateId,
                sender,
                recipient,
                cc,
                subject,
                body,
                params,
                attachments,
                status
        );

        commandBus.execute(command);
    }
}
