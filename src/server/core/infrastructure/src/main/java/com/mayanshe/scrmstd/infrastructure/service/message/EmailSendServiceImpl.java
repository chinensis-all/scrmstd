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
package com.mayanshe.scrmstd.infrastructure.service.message;

import com.mayanshe.scrmstd.message.send.service.EmailSendService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * SimpleEmailSender: 基于JavaMailSender的简单邮件发送实现
 */
@Component
public class EmailSendServiceImpl implements EmailSendService {
    private final JavaMailSender sender;

    public EmailSendServiceImpl(JavaMailSender sender) {
        this.sender = sender;
    }

    @Override
    public boolean send(String from, String to, List<String> cc, String subject, String body, Map<String, String> attachments) {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            if (cc != null && !cc.isEmpty()) {
                helper.setCc(cc.toArray(new String[0]));
            }

            if (attachments != null) {
                for (Map.Entry<String, String> entry : attachments.entrySet()) {
                    String fileName = entry.getKey();
                    String filePath = entry.getValue();
                    File file = new File(filePath);
                    if (file.exists() && file.isFile()) {
                        helper.addAttachment(fileName, file);
                    }
                }
            }

            sender.send(message);
        } catch (MessagingException e) {
            return false;
        }

        return true;
    }
}
