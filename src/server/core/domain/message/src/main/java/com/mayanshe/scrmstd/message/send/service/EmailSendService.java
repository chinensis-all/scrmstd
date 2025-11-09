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
package com.mayanshe.scrmstd.message.send.service;

import java.util.List;
import java.util.Map;

/**
 * SenEmailService: 邮件发送服务接口
 */
public  interface EmailSendService {
    /**
     * 发送邮件
     *
     * @param from        来自
     * @param recipient   收件人
     * @param cc          抄送列表
     * @param subject     主题
     * @param body        邮件内容
     * @param attachments 附件列表 key: 文件名 value: 文件路径
     * @return 发送结果
     */
    boolean send(String from, String recipient, List<String> cc, String subject, String body, Map<String, String> attachments);

    public record EmailAttachment(
            String fileName,
            String filePath,
            String contentType
    ) {}
}
