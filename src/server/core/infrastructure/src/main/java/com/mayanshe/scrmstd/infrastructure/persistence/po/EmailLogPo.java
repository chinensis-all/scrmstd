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

import java.util.List;
import java.util.Map;

/**
 * EmailLogPo: 邮件发送日志持久化对象
 */
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class EmailLogPo {
    private Long id;                           // 日志ID

    @Builder.Default
    private Long tenantId = 0L;                // 租户ID (0表示系统级)

    @Builder.Default
    private Long taskId = 0L;                  // 任务ID (0表示立即发送)

    @Builder.Default
    private Long templateId = 0L;              // 模板ID (0表示未使用模板)

    private String sender;                     // 发件人邮箱

    private String recipient;                  // 收件人邮箱

    private String cc;                         // 抄送邮箱(JSON格式)

    private String subject;                    // 邮件主题

    private String body;                       // 邮件正文(渲染后)

    private String params;                     // 邮件原始数据(JSON格式)

    private String attachments;                // 附件信息(JSON格式)

    private String status;                     // 发送状态

    @Builder.Default
    private Long createdAt = 0L;               // 创建时间

    @Builder.Default
    private Long updatedAt = 0L;               // 更新时间

    @Builder.Default
    private Long version = 0L;                 // 乐观锁版本号
}
