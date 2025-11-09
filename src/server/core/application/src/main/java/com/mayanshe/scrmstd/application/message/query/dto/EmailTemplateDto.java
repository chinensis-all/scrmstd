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
package com.mayanshe.scrmstd.application.message.query.dto;

import com.mayanshe.scrmstd.message.template.type.EmailTemplateCategory;
import com.mayanshe.scrmstd.shared.util.DateUtil;

import java.util.List;

/**
 * 邮件模板DTO
 *
 * @param id           模板ID
 * @param tenantId     租户ID
 * @param templateName 模板名称
 * @param category     模板类别
 * @param subject      邮件主题
 * @param body         邮件正文
 * @param placeholders 占位符列表
 * @param createdAt    创建时间
 * @param updatedAt    更新时间
 */
public record EmailTemplateDto(
        String id,
        Long tenantId,
        String templateName,
        EmailTemplateCategory category,
        String subject,
        String body,
        List<String> placeholders,
        String createdAt,
        String updatedAt
) {
    @Override
    public String createdAt() {
        return createdAt == null || createdAt.isBlank() || createdAt.equals("0") ? "" : DateUtil.timestampToFormattedDate(createdAt);
    }

    @Override
    public String updatedAt() {
        return updatedAt == null || updatedAt.isBlank() || updatedAt.equals("0") ? "" : DateUtil.timestampToFormattedDate(updatedAt);
    }
}
