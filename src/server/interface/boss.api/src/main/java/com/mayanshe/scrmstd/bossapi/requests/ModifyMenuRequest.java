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
import lombok.Data;

/**
 * ModifyMenuRequest: 修改菜单请求参数
 */
@Data
public class ModifyMenuRequest {
    private String parentId;

    @jakarta.validation.constraints.NotNull(message = "菜单类型不能为空")
    private Byte kind;

    @NotBlank(message = "路由名称不能为空")
    private String name;

    @NotBlank(message = "菜单名称不能为空")
    private String title;

    private String path;
    private String redirect;
    private String component;
    private String icon;
    private Integer sort;
    private Byte isExternal;
    private String externalLink;
    private Byte keepAlive;
    private Byte hideInMenu;
    private Byte hideChildrenInMenu;
    private Byte requiresAuth;
    private String permission;
    private Byte status;
    private String remark;
}
