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
package com.mayanshe.scrmstd.application.boss.command.handler;

import cn.dev33.satoken.stp.StpUtil;
import com.mayanshe.scrmstd.application.CommandHandler;
import com.mayanshe.scrmstd.application.boss.command.LoginBossAdminCommand;
import com.mayanshe.scrmstd.domain.boss.admin.model.BossAdmin;
import com.mayanshe.scrmstd.domain.boss.admin.repo.BossAdminRepository;
import com.mayanshe.scrmstd.shared.exception.BadRequestException;
import com.mayanshe.scrmstd.shared.model.AccessToken;
import org.springframework.stereotype.Service;

@Service
public class LoginBossAdminHandler implements CommandHandler<LoginBossAdminCommand, AccessToken> {
    private final BossAdminRepository repository;

    public LoginBossAdminHandler(BossAdminRepository repository) {
        this.repository = repository;
    }

    @Override
    public AccessToken handle(LoginBossAdminCommand command) {
        BossAdmin admin = repository.loadByAccount(command.account()).orElseThrow(() -> new BadRequestException("登录失败，账号或密码错误"));

        if (!admin.verifyPassword(command.password())) {
            throw new BadRequestException("登录失败，账号或密码错误");
        }

        StpUtil.login(admin.getId().id(), command.rememberMe());

        StpUtil.getSession().set("username", admin.getUsername());
        StpUtil.getSession().set("fullName", admin.getFullName());

        return new AccessToken("Bearer", StpUtil.getTokenValue());
    }
}
