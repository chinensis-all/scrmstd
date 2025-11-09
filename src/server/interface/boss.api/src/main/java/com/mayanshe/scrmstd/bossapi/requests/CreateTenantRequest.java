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

import com.mayanshe.scrmstd.infrastructure.support.validation.NotBlankPattern;
import com.mayanshe.scrmstd.infrastructure.support.validation.NotBlankSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateTenantRequest {
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

    @NotNull(message = "请选择所在省")
    private Long provinceId;                   // 所在省ID

    @NotNull(message = "请选择所在市")
    private Long cityId;                       // 所在市ID

    @NotNull(message = "请选择所在区县")
    private Long districtId;                   // 所在区县ID

    @NotBlank(message = "详细地址不能为空")
    @Size(message = "详细地址长度不能超过255个字符", max = 255)
    private String address;                    // 详细地址

    @NotBlank(message = "联系人姓名不能为空")
    @Size(message = "联系人姓名长度不能超过125个字符", max = 125)
    private String contactPerson;              // 联系人姓名

    @NotBlank(message = "联系电话不能为空")
    @Pattern(message = "联系电话格式不正确", regexp = "^(1[3-9]\\d{9}|0\\d{2,3}-?\\d{7,8})$")
    private String contactPhone;               // 联系电话（支持中国手机号格式）

    @NotBlankPattern(message = "联系邮箱格式错误", regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    @NotBlankSize(max = 125, message = "联系邮箱不能为空且长度不能超过125个字符")
    private String contactEmail;               // 联系邮箱

    @NotBlankSize(max = 50, message = "联系微信号不能为空且长度不能超过50个字符")
    private String contactWechat;              // 联系微信号（中国市场常用）
}
