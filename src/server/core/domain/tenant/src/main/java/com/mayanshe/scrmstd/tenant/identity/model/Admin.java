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
package com.mayanshe.scrmstd.tenant.identity.model;

import com.mayanshe.scrmstd.shared.base.Aggregate;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import com.mayanshe.scrmstd.tenant.identity.event.AdminActivatedEvent;
import com.mayanshe.scrmstd.tenant.identity.event.AdminCreatedEvent;
import com.mayanshe.scrmstd.tenant.identity.event.AdminDestroyedEvent;
import com.mayanshe.scrmstd.tenant.identity.event.AdminModifiedEvent;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Admin: 租户管理员聚合根
 */
@Getter
@Setter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends Aggregate {
    private AggregateId id;                            // ID

    private Long tenantId;                             // 租户ID

    private String username;                           // 用户名

    private String password;                           // 密码（加密存储）

    private String fullName;                           // 姓名

    private String email;                              // 邮箱

    private String phone;                              // 联系电话

    private String avatar;                             // 头像URL

    private Long lastLoginAt = 0L;                     // 最后登录时间

    private String lastLoginIp = "";                   // 最后登录IP

    @Builder.Default
    private Boolean deleted = false;                   // 逻辑删除标志

    private List<Long> roleIds = new ArrayList<>();    // 关联角色ID列表

    public void addRoleId(Long roleId) {
        if (!this.roleIds.contains(roleId)) {
            this.roleIds.add(roleId);
        }
    }

    public void removeRoleId(Long roleId) {
        this.roleIds.remove(roleId);
    }

    public void clearRoleIds() {
        this.roleIds.clear();
    }

    /**
     * 创建管理员
     */
    public void create() {
        this.setDeleted(false);

        AdminCreatedEvent event = AdminCreatedEvent.builder()
                .refId(this.getId().id())
                .adminId(this.getId())
                .tenantId(this.getTenantId())
                .username(this.getUsername())
                .fullName(this.getFullName())
                .email(this.getEmail())
                .phone(this.getPhone())
                .avatar(this.getAvatar())
                .build();
        this.registerEvent(event);
    }

    /**
     * 修改管理员信息
     */
    public void modify() {
        this.setDeleted(false);

        AdminModifiedEvent event = AdminModifiedEvent.builder()
                .refId(this.getId().id())
                .adminId(this.getId())
                .tenantId(this.getTenantId())
                .username(this.getUsername())
                .fullName(this.getFullName())
                .email(this.getEmail())
                .phone(this.getPhone())
                .avatar(this.getAvatar())
                .build();
        this.registerEvent(event);
    }

    /**
     * 删除管理员
     */
    public void destroyed() {
        this.setDeleted(true);

        AdminDestroyedEvent event = AdminDestroyedEvent.builder().refId(this.getId().id()).adminId(this.getId()).build();
        this.registerEvent(event);
    }

    /**
     * 激活管理员
     */
    public void activate() {
        this.setDeleted(false);
        this.clearRoleIds();

        AdminActivatedEvent event = AdminActivatedEvent.builder().refId(this.getId().id()).adminId(this.getId()).build();
        this.registerEvent(event);
    }
}
