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
package com.mayanshe.scrmstd.bossapi.requests;

import com.mayanshe.scrmstd.infrastructure.support.validation.NotBlankSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * ModifyTenantBaseRequest: 修改租户基础信息请求载体
 */
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ModifyTenantBaseRequest {
    @NotBlank(message = "租户名称不能为空")
    @Size(message = "租户名称长度不能超过100个字符", max = 100)
    private String tenantName;                 // 租户名称（企业或组织名称）

    @NotBlank(message = "法人姓名不能为空")
    @NotBlankSize(message = "法人姓名不能为空且长度不能超过50个字符", max = 125)
    private String legalName;                  // 法人姓名

    @NotBlank(message = "统一社会信用代码不能为空")
    @Pattern(message = "统一社会信用代码格式不正确", regexp = "^[0-9A-Z]{18}$")
    private String creditCode;                 // 统一社会信用代码

    @NotBlank(message = "请选择行业分类")
    private String industryCode;               // 行业分类代码（基于 GB/T 4754-2017，例如 A01 为农业）
}
