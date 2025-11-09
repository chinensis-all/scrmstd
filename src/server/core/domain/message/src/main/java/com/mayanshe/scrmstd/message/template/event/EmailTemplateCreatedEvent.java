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
package com.mayanshe.scrmstd.message.template.event;

import com.mayanshe.scrmstd.message.template.type.EmailTemplateCategory;
import com.mayanshe.scrmstd.shared.base.DomainEvent;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * EmailTemplateCreatedEvent: 邮件模板已创建事件
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplateCreatedEvent extends DomainEvent {
    private AggregateId templateId;

    private Long tenantId;

    private String templateName;

    private EmailTemplateCategory category;

    private String subject;

    private String body;

    private List<String> placeHolders;
}
