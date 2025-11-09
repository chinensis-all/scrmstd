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

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AdminPo {
    private Long id;                 // ID

    private Long tenantId;           // 租户ID

    private String username;         // 用户名

    private String password;         // 密码（加密存储）

    private String fullName;         // 姓名

    private String email;            // 邮箱

    private String phone;            // 联系电话

    private String avatar;           // 头像URL

    private Long lastLoginAt;        // 最后登录时间

    private String lastLoginIp;      // 最后登录IP

    @Builder.Default
    private Long createdAt = 0L;     // 创建时间

    @Builder.Default
    private Long updatedAt = 0L;     // 更新时间

    @Builder.Default
    private Long deletedAt = 0L;     // 软删除时间
}
