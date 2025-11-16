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

/**
 * PermissionGroupPo: 权限组持久化对象
 */
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PermissionGroupPo {
    private Long id;              // 主键ID

    private Long parentId;        // 父权限组ID

    private String groupName;     // 权限组名称

    private String displayName;   // 显示名称

    private String description;   // 权限组描述

    @Builder.Default
    private Long createdAt = 0L;  // 创建时间

    @Builder.Default
    private Long updatedAt = 0L;  // 更新时间

    @Builder.Default
    private Long deletedAt = 0L;  // 软删除时间
}
