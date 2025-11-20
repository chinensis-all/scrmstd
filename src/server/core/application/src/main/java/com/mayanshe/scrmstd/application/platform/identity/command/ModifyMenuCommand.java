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
package com.mayanshe.scrmstd.application.platform.identity.command;

import com.mayanshe.scrmstd.application.Command;
import lombok.*;

/**
 * ModifyMenuCommand: 修改菜单命令
 */
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ModifyMenuCommand extends Command {
    private Long id;
    private Long parentId;
    private Byte kind;
    private String name;
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
