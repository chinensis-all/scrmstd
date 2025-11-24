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

import com.mayanshe.scrmstd.infrastructure.support.validation.AnyInList;
import com.mayanshe.scrmstd.infrastructure.support.validation.NotBlankSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

/**
 * ModifyMenuRequest: 修改菜单请求参数
 */
@Data
@Builder(toBuilder = true)
public class ModifyMenuRequest {
    private String parentId;

    @NotNull(message = "菜单类型不能为空")
    @AnyInList(value = {"1","2"}, message = "菜单类型只能是 菜单 或 按钮")
    private Byte kind;

    @NotBlank(message = "路由名称不能为空")
    @Size(max = 100, message = "路由名称长度不能超过100个字符")
    private String name;

    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 100, message = "菜单名称长度不能超过100个字符")
    private String title;

    @NotBlankSize(max = 255, message = "路由地址长度不能超过200个字符")
    @Builder.Default
    private String path = "";

    @NotBlankSize(max = 255, message = "路由重定向地址长度不能超过200个字符")
    @Builder.Default
    private String redirect = "";

    @NotBlankSize(max = 255, message = "组件路径长度不能超过200个字符")
    @Builder.Default
    private String component = "";

    @NotBlankSize(max = 50, message = "图标长度不能超过100个字符")
    @Builder.Default
    private String icon ="";

    @Builder.Default
    private Integer sort = 0;

    @Builder.Default
    private Boolean isExternal = false;

    @Builder.Default
    private String externalLink = "";

    @Builder.Default
    private Boolean keepAlive = false;

    @Builder.Default
    private Boolean hideInMenu = false;

    @Builder.Default
    private Boolean hideChildrenInMenu = false;

    @Builder.Default
    private Boolean requiresAuth = false;

    @Builder.Default
    private String permission = "";

    @Builder.Default
    private Byte status = 1;

    @Builder.Default
    private String remark = "";
}
