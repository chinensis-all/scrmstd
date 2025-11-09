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
package com.mayanshe.scrmstd.generic.support.model;

import com.mayanshe.scrmstd.shared.base.Aggregate;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    private Long id;                 // ID

    private String identity;         // 联系人身份（系统管理员、租户管理员、普通用户）

    private String fullName;         // 联系人姓名

    private String email;            // 联系人邮箱

    private String phone;            // 联系人手机号
}
