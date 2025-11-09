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
package com.mayanshe.scrmstd.infrastructure.persistence.po;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * EmailTemplatePo: 邮件模板持久化对象
 */
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplatePo {
    private Long id;                           // 模板ID

    @Builder.Default
    private Long tenantId = 0L;                // 租户ID (0表示系统级模板)

    private String templateName;               // 模板名称

    private String category;                   // 模板类别

    private String subject;                    // 邮件主题

    private String body;                       // 邮件正文(支持HTML)

    private String placeholders;               // 占位符列表(JSON格式存储)

    @Builder.Default
    private Long createdAt = 0L;               // 创建时间

    @Builder.Default
    private Long updatedAt = 0L;               // 更新时间

    @Builder.Default
    private Long deletedAt = 0L;               // 软删除时间

    @Builder.Default
    private Long version = 0L;                 // 乐观锁版本号
}
