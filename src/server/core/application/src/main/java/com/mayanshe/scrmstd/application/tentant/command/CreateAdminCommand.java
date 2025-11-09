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
package com.mayanshe.scrmstd.application.tentant.command;

import com.mayanshe.scrmstd.application.Command;
import com.mayanshe.scrmstd.shared.model.IDResponse;

import java.util.List;

/**
 * CreateAdminCommand: 创建租户管理员命令载体
 *
 * @param tenantId     租户ID
 * @param username     用户名
 * @param password     密码
 * @param fullName     姓名
 * @param email        邮箱
 * @param phone        手机号
 * @param avatar       头像URL
 */
public record CreateAdminCommand(
        Long tenantId,
        String username,
        String password,
        String fullName,
        String email,
        String phone,
        String avatar,
        List<Long> roleIds,
        Boolean initial
) implements Command<IDResponse> {}