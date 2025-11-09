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

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ModifyTenantLocationRequest {
    @NotNull(message = "请选择所在省")
    private Long provinceId;                   // 所在省ID

    @NotNull(message = "请选择所在市")
    private Long cityId;                       // 所在市ID

    @NotNull(message = "请选择所在区县")
    private Long districtId;                   // 所在区县ID

    @NotBlank(message = "详细地址不能为空")
    @Size(message = "详细地址长度不能超过255个字符", max = 255)
    private String address;                    // 详细地址
}
