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
package com.mayanshe.scrmstd.saasapi.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmailTemplateRequest {
    @Builder.Default
    private Long tenantId = 0L;

    @NotBlank(message = "模板名称不能为空")
    @Size(min = 2, max = 100, message = "模板名称长度必须在2到100个字符之间")
    private String templateName;

    @NotBlank(message = "模板类别不能为空")
    private String category;

    @NotBlank(message = "邮件主题不能为空")
    @Size(max = 255, message = "邮件主题长度不能超过255个字符")
    private String subject;

    @NotBlank(message = "邮件正文不能为空")
    private String body;

    private List<String> placeholders;
}
