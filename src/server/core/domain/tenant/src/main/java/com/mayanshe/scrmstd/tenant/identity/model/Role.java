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
import com.mayanshe.scrmstd.shared.exception.BadRequestException;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import com.mayanshe.scrmstd.tenant.identity.event.RoleCreatedEvent;
import com.mayanshe.scrmstd.tenant.identity.event.RoleDeletedEvent;
import com.mayanshe.scrmstd.tenant.identity.event.RoleModifiedEvent;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Role extends Aggregate {
    private AggregateId id;               // ID

    private Long tenantId;                // 租户ID

    private String roleName;              // 角色名称

    private String description;           // 角色描述

    private Boolean deleted = false;      // 逻辑删除标志

    /**
     * 创建角色
     *
     * @param id          角色ID
     * @param tenantId    租户ID
     * @param roleName    角色名称
     * @param description 角色描述
     */
    public void create(AggregateId id, Long tenantId, String roleName, String description) {
        this.setDeleted(false);
        this.setId(id);
        this.setTenantId(tenantId);
        this.setRoleName(roleName);
        this.setDescription(description);

        RoleCreatedEvent event = RoleCreatedEvent.builder().refId(id.id()).roleId(this.getId()).tenantId(tenantId).roleName(roleName).description(description).build();
        this.registerEvent(event);
    }

    /**
     * 修改角色
     *
     * @param roleName    角色名称
     * @param description 角色描述
     */
    public void modify(String roleName, String description) {
        this.setDeleted(false);
        this.setRoleName(roleName);
        this.setDescription(description);

        RoleModifiedEvent event = RoleModifiedEvent.builder().refId(this.getId().id()).adminId(this.getId()).build();
        this.registerEvent(event);
    }

    /**
     * 删除角色
     */
    public void delete() {
        if (this.getRoleName().equals("超级管理员")) {
            throw new BadRequestException("无法删除系统内置角色：超级管理员");
        }

        this.setDeleted(true);
        RoleDeletedEvent event = RoleDeletedEvent.builder().adminId(this.getId()).build();
        this.registerEvent(event);
    }
}
