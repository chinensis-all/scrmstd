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
 * MenuPo: 菜单持久化对象
 */
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class MenuPo {
    private Long id;

    @Builder.Default
    private Long parentId = 0L;

    @Builder.Default
    private Byte kind = 1;

    @Builder.Default
    private String name = "";

    @Builder.Default
    private String title = "";

    @Builder.Default
    private String path = "";

    @Builder.Default
    private String redirect = "";

    @Builder.Default
    private String component = "";

    @Builder.Default
    private String icon = "";

    @Builder.Default
    private Integer sort = 0;

    @Builder.Default
    private Byte isExternal = 0;

    @Builder.Default
    private String externalLink = "";

    @Builder.Default
    private Byte keepAlive = 0;

    @Builder.Default
    private Byte hideInMenu = 0;

    @Builder.Default
    private Byte hideChildrenInMenu = 0;

    @Builder.Default
    private Byte requiresAuth = 1;

    @Builder.Default
    private String permission = "";

    @Builder.Default
    private Byte status = 1;

    @Builder.Default
    private String remark = "";

    @Builder.Default
    private Long createdAt = 0L;

    @Builder.Default
    private Long updatedAt = 0L;
}
