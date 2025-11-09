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

import com.mayanshe.scrmstd.message.template.model.EmailTemplate;
import com.mayanshe.scrmstd.message.template.repo.EmailTemplateRepository;
import com.mayanshe.scrmstd.message.template.service.EmailTemplateRenderService;
import com.mayanshe.scrmstd.shared.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * EmailTemplateRenderServiceImpl: 邮件模板渲染服务实现
 */
@Slf4j
@Service
public class EmailTemplateRenderServiceImpl implements EmailTemplateRenderService {
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{\\s*(\\w+)\\s*\\}\\}");

    private final EmailTemplateRepository templateRepository;

    public EmailTemplateRenderServiceImpl(EmailTemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Override
    public RenderedEmail render(Long templateId, Map<String, Object> params) {
        EmailTemplate template = templateRepository.load(templateId)
                .orElseThrow(() -> new ResourceNotFoundException("邮件模板不存在，ID=" + templateId));

        String renderedSubject = renderText(template.getSubject(), params);
        String renderedBody = renderText(template.getBody(), params);

        return new RenderedEmail(renderedSubject, renderedBody);
    }

    @Override
    public String renderText(String template, Map<String, Object> params) {
        if (template == null || template.isEmpty()) {
            return "";
        }

        if (params == null || params.isEmpty()) {
            return template;
        }

        // 使用StringBuilder和Matcher进行高效替换
        // 这比多次String.replace()快得多，特别是对于大模板和多个占位符
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(template);
        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            String placeholder = matcher.group(1);
            Object value = params.get(placeholder);

            if (value != null) {
                // 转义特殊字符以避免在appendReplacement中出现问题
                String replacement = Matcher.quoteReplacement(value.toString());
                matcher.appendReplacement(result, replacement);
            } else {
                // 如果参数不存在，保留原占位符或替换为空字符串
                log.warn("模板参数缺失: {}", placeholder);
                matcher.appendReplacement(result, "");
            }
        }
        matcher.appendTail(result);

        return result.toString();
    }
}
