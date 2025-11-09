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
package com.mayanshe.scrmstd.message.template.service;

import java.util.Map;

/**
 * EmailTemplateRenderService: 邮件模板渲染服务接口
 */
public interface EmailTemplateRenderService {
    /**
     * 渲染邮件模板
     *
     * @param templateId 模板ID
     * @param params     参数Map
     * @return 渲染后的邮件内容(包含主题和正文)
     */
    RenderedEmail render(Long templateId, Map<String, Object> params);

    /**
     * 渲染邮件模板文本
     *
     * @param template 模板文本
     * @param params   参数Map
     * @return 渲染后的文本
     */
    String renderText(String template, Map<String, Object> params);

    /**
     * 渲染后的邮件内容
     *
     * @param subject 邮件主题
     * @param body    邮件正文
     */
    record RenderedEmail(String subject, String body) {
    }
}
