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
package com.mayanshe.scrmstd.application.tenant.command;

import com.mayanshe.scrmstd.application.Command;

/**
 * ModifyPermissionCommand: 修改权限命令
 *
 * @param id              权限ID
 * @param groupId         权限组ID
 * @param permissionName  权限名称
 * @param displayName     权限显示名称
 * @param description     权限描述
 */
public record ModifyPermissionCommand(
        Long id,
        Long groupId,
        String permissionName,
        String displayName,
        String description
) implements Command<Boolean> {
}
