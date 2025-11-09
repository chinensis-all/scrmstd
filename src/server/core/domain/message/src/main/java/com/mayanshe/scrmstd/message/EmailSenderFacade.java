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
package com.mayanshe.scrmstd.message;

import com.mayanshe.scrmstd.message.send.event.EmailSendFailedEvent;
import com.mayanshe.scrmstd.message.send.event.EmailSentEvent;
import com.mayanshe.scrmstd.message.send.service.EmailSendService;
import com.mayanshe.scrmstd.message.template.repo.EmailTemplateRepository;
import com.mayanshe.scrmstd.message.template.service.EmailTemplateRenderService;
import com.mayanshe.scrmstd.shared.base.DomainEvent;
import com.mayanshe.scrmstd.shared.exception.InternalServerException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class EmailSenderFacade {
    private final EmailTemplateRepository templateRepository;

    private final EmailTemplateRenderService templateRenderService;

    private final EmailSendService emailSendService;

    public EmailSenderFacade(
            EmailTemplateRepository templateRepository,
            EmailTemplateRenderService templateRenderService,
            EmailSendService emailSendService
    ) {
        this.templateRepository = templateRepository;
        this.templateRenderService = templateRenderService;
        this.emailSendService = emailSendService;
    }

    /**
     * 发送邮件
     *
     * @param request 发送请求
     * @return 领域事件
     */
    public DomainEvent send(EmailSendRequest request) {
        var template = templateRepository.load(request.templateId).orElseThrow(
                () -> new InternalServerException("此邮件模版不存在: " + request.templateId)
        );

        String subject = String.format("%s %s", template.getSubject(), request.subtitle);
        String body = templateRenderService.renderText(template.getBody(), request.params);

        boolean result = emailSendService.send(
                request.from,
                request.recipient,
                request.cc,
                subject,
                body,
                request.attachments
        );

        if (result) {
            return EmailSentEvent.builder()
                    .tenantId(request.tenantId)
                    .from(request.from)
                    .recipient(request.recipient)
                    .subject(subject)
                    .templateId(request.templateId)
                    .params(request.params)
                    .cc(request.cc)
                    .attachments(request.attachments)
                    .build();
        }

        return EmailSendFailedEvent.builder()
                .tenantId(request.tenantId)
                .from(request.from)
                .recipient(request.recipient)
                .subject(request.subtitle)
                .templateId(request.templateId)
                .params(request.params)
                .cc(request.cc)
                .attachments(request.attachments)
                .build();
    }

    /**
     * 发送邮件请求
     *
     * @param tenantId    租户ID
     * @param from        来源ID
     * @param recipient   收件人
     * @param subtitle    副标题
     * @param templateId  模板ID
     * @param params      模板参数
     * @param cc          抄送列表
     * @param attachments 附件列表 key: 文件名 value: 文件路径
     */
    public record EmailSendRequest(
            Long tenantId,
            String from,
            String recipient,
            String subtitle,
            Long templateId,
            Map<String, Object> params,
            List<String> cc,
            Map<String, String> attachments
    ) {}
}
