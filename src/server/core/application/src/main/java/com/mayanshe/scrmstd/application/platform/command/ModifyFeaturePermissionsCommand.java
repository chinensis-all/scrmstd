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

import java.util.Set;

/**
 * ModifyFeaturePermissionCommand: 修改功能点权限命令
 *
 * @param featureId     功能点ID
 * @param permissionIds 权限ID集合
 */
public record ModifyFeaturePermissionsCommand(
        Long featureId,
        Set<Long> permissionIds
) implements Command<Boolean> {}
