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
package com.mayanshe.scrmstd.application.boss.command;

import com.mayanshe.scrmstd.application.Command;
import com.mayanshe.scrmstd.shared.model.AccessToken;

/**
 * LoginBossAdminCommand: 登录命令载体
 *
 * @param account
 * @param password
 * @param rememberMe
 */
public record LoginBossAdminCommand(String account, String password,
                                    Boolean rememberMe) implements Command<AccessToken> {
    @Override
    public Boolean rememberMe() {
        return rememberMe == null ? Boolean.FALSE : rememberMe;
    }
}
