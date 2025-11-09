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
package com.mayanshe.scrmstd.domain.boss.admin.model;

import cn.hutool.crypto.digest.BCrypt;
import com.mayanshe.scrmstd.domain.boss.admin.event.*;
import com.mayanshe.scrmstd.shared.base.Aggregate;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BossAdmin extends Aggregate {
    private AggregateId id;                   // ID

    private String username;                  // 用户名

    private String password;                  // 密码

    private String fullName;                  // 真实姓名

    private String phone;                     // 手机号

    private String email;                     // 邮箱

    private String avatar;                    // 头像

    @Builder.Default
    private Long lastLoginAt = 0L;            // 最后登录时间

    @Builder.Default
    private String lastLoginIp = "";          // 最后登录IP

    @Builder.Default
    private boolean deleted = false;          // 是否删除

    /**
     * 设置密码（加密）
     *
     * @param password 明文密码
     */
    public void setSourcePassword(String password) {
        if (password != null && !password.isEmpty() && !password.contains("*")) {
            this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        }
    }

    /**
     * 验证密码
     *
     * @param password 明文密码
     * @return 验证结果
     */
    public boolean verifyPassword(String password) {
        return BCrypt.checkpw(password, this.password);
    }

    /**
     * 创建
     */
    public void create() {
        this.setDeleted(false);

        BossAdminCreatedEvent event = BossAdminCreatedEvent.builder().refId(this.getId() != null ? this.getId().id() : 0L).bossAdminId(this.getId()).username(this.getUsername()).fullName(this.getFullName()).mobile(this.getPhone()).email(this.getEmail()).build();
        this.registerEvent(event);
    }

    /**
     * 修改
     */
    public void modify(String username, String password, String fullName, String phone, String email, String avatar) {
        this.setDeleted(false);
        this.setUsername(username);
        this.setFullName(fullName);
        this.setPhone(phone);
        this.setEmail(email);
        this.setAvatar(avatar);
        this.setSourcePassword(password);

        BossAdminModifiedEvent event = BossAdminModifiedEvent.builder().refId(this.getId() != null ? this.getId().id() : 0L).bossAdminId(this.getId()).username(this.getUsername()).fullName(this.getFullName()).mobile(this.getPhone()).email(this.getEmail()).build();
        this.registerEvent(event);
    }

    /**
     * 禁用
     */
    public void destroy() {
        this.setDeleted(true);

        BossAdminDestroyedEvent event = BossAdminDestroyedEvent.builder().refId(this.getId() != null ? this.getId().id() : 0L).bossAdminId(this.getId()).build();
        this.registerEvent(event);
    }

    /**
     * 启用
     */
    public void activate() {
        this.setDeleted(false);

        BossAdminActivatedEvent event = BossAdminActivatedEvent.builder().refId(this.getId() != null ? this.getId().id() : 0L).bossAdminId(this.getId()).build();
        this.registerEvent(event);
    }

    /**
     * 登录
     *
     * @param ipAddress 登录IP
     */
    public void Login(String ipAddress) {
        this.setLastLoginAt(System.currentTimeMillis());
        this.setLastLoginIp(ipAddress);

        BossAdminLoginEvent event = BossAdminLoginEvent.builder().refId(this.getId() != null ? this.getId().id() : 0L).bossAdminId(this.getId()).loginIp(ipAddress).loginAt(this.getLastLoginAt()).build();
        this.registerEvent(event);
    }

    /**
     * 登出
     *
     * @param ipAddress 登出IP
     */
    public void logout(String ipAddress) {
        BossAdminLogoutEvent event = BossAdminLogoutEvent.builder().refId(this.getId() != null ? this.getId().id() : 0L).bossAdminId(this.getId()).logoutIp(ipAddress).logoutAt(System.currentTimeMillis()).build();
        this.registerEvent(event);
    }
}
