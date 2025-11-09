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
package com.mayanshe.scrmstd.application.message.command;

import com.mayanshe.scrmstd.application.Command;

import java.util.List;

/**
 * 创建邮件模板命令
 *
 * @param tenantId     租户ID (0表示系统级模板)
 * @param templateName 模板名称
 * @param category     模板类别
 * @param subject      邮件主题
 * @param body         邮件正文
 * @param placeholders 占位符列表
 */
public record CreateEmailTemplateCommand(
        Long tenantId,
        String templateName,
        String category,
        String subject,
        String body,
        List<String> placeholders
) implements Command<Long> {
}
