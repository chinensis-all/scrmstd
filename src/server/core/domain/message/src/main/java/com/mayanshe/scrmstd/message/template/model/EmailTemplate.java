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
package com.mayanshe.scrmstd.message.template.model;

import com.mayanshe.scrmstd.message.template.event.EmailTemplateCreatedEvent;
import com.mayanshe.scrmstd.message.template.event.EmailTemplateDeletedEvent;
import com.mayanshe.scrmstd.message.template.event.EmailTemplateModifiedEvent;
import com.mayanshe.scrmstd.message.template.type.EmailTemplateCategory;
import com.mayanshe.scrmstd.shared.base.Aggregate;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import lombok.*;

import java.util.List;

/**
 * EmailTemplate: 邮件模板聚合根
 */
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplate extends Aggregate {
    private AggregateId id;

    @Builder.Default
    private Long tenantId = 0L;

    private String templateName;

    private EmailTemplateCategory category;

    private String subject;

    private String body;

    private List<String> placeholders;

    @Builder.Default
    private boolean deleted = false;

    @Builder.Default
    private Long version = 0L;

    /**
     * 创建模板
     */
    public void create() {
        this.setDeleted(false);

        EmailTemplateCreatedEvent event = EmailTemplateCreatedEvent.builder()
                .refId(this.getId().id())
                .templateId(this.getId())
                .tenantId(this.getTenantId())
                .templateName(this.getTemplateName())
                .category(this.getCategory())
                .subject(this.getSubject())
                .body(this.getBody())
                .placeHolders(this.getPlaceholders())
                .build();
        this.registerEvent(event);
    }

    /**
     * 修改模板
     *
     * @param templateName 模板名称
     * @param category     模板类别
     * @param subject      邮件主题
     * @param body         邮件正文
     * @param placeholders 占位符列表
     */
    public void modify(String templateName, EmailTemplateCategory category, String subject, String body, List<String> placeholders) {
        this.setTemplateName(templateName);
        this.setCategory(category);
        this.setSubject(subject);
        this.setBody(body);
        this.setPlaceholders(placeholders);

        EmailTemplateModifiedEvent event = EmailTemplateModifiedEvent.builder()
                .refId(this.getId() != null ? this.getId().id() : 0L)
                .templateId(this.getId()).tenantId(this.getTenantId())
                .templateName(this.getTemplateName())
                .category(this.getCategory())
                .subject(this.getSubject())
                .body(this.getBody())
                .placeHolders(this.getPlaceholders())
                .build();
        this.registerEvent(event);
    }

    /**
     * 删除模板
     */
    public void delete() {
        this.setDeleted(true);

        EmailTemplateDeletedEvent event = EmailTemplateDeletedEvent.builder()
                .refId(this.getId() != null ? this.getId().id() : 0L)
                .templateId(this.getId())
                .build();
        this.registerEvent(event);
    }
}
