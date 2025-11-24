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
package com.mayanshe.scrmstd.application.platform.command;

import com.mayanshe.scrmstd.application.Command;

/**
 * CreateMenuCommand : 创建菜单命令
 *
 * @param parentId
 * @param kind
 * @param name
 * @param title
 * @param path
 * @param redirect
 * @param component
 * @param icon
 * @param sort
 * @param isExternal
 * @param externalLink
 * @param keepAlive
 * @param hideInMenu
 * @param hideChildrenInMenu
 * @param requiresAuth
 * @param permission
 * @param status
 * @param remark
 */
public record CreateMenuCommand(
    Long parentId,
    Byte kind,
    String name,
    String title,
    String path,
    String redirect,
    String component,
    String icon,
    Integer sort,
    Boolean isExternal,
    String externalLink,
    Boolean keepAlive,
    Boolean hideInMenu,
    Boolean hideChildrenInMenu,
    Boolean requiresAuth,
    String permission,
    Byte status,
    String remark
) implements Command<Long> {}
