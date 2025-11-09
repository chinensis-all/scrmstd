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
package com.mayanshe.scrmstd.bossapi.config;

import cn.dev33.satoken.stp.StpUtil;
import com.mayanshe.scrmstd.shared.model.UserDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

/**
 * UserDetailConfig: 登录用户配置
 */
@Configuration
public class UserDetailConfig {
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public UserDetail userDetail() {
        if (StpUtil.isLogin()) {
            Long id = StpUtil.getLoginIdAsLong();
            String username = StpUtil.getSession().get("username", "unknown");
            String fullName = StpUtil.getSession().get("fullName", "unknown");
            return new UserDetail(id, username, fullName);
        }
                
        return new UserDetail(0L, "system", "系统用户");
    }
}
