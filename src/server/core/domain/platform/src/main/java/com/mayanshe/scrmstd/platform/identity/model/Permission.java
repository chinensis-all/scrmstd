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
package com.mayanshe.scrmstd.platform.identity.model;

import com.mayanshe.scrmstd.shared.base.Aggregate;
import com.mayanshe.scrmstd.shared.exception.BadRequestException;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import com.mayanshe.scrmstd.platform.identity.event.PermissionCreatedEvent;
import com.mayanshe.scrmstd.platform.identity.event.PermissionDeletedEvent;
import com.mayanshe.scrmstd.platform.identity.event.PermissionModifiedEvent;
import lombok.*;

/**
 * Permission: 权限聚合根
 */
@Getter
@Setter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends Aggregate {
    private AggregateId id;

    private Long groupId;

    private String permissionName;

    private String displayName;

    private String description;

    @Builder.Default
    private Boolean deleted = false;

    /**
     * 创建权限
     */
    public void create() {
        this.setDeleted(false);

        PermissionCreatedEvent event = PermissionCreatedEvent.builder()
                .refId(this.getId().id())
                .permissionId(this.getId())
                .groupId(this.getGroupId())
                .permissionName(this.getPermissionName())
                .displayName(this.getDisplayName())
                .description(this.getDescription())
                .build();
        this.registerEvent(event);
    }

    /**
     * 修改权限
     * @param groupId   权限组ID
     * @param permissionName  权限名称
     * @param displayName 显示名称
     * @param description 描述
     */
    public void modify(Long groupId, String permissionName, String displayName, String description) {
        this.setGroupId(groupId);
        this.setPermissionName(permissionName);
        this.setDisplayName(displayName);
        this.setDescription(description);
        this.setDeleted(false);

        PermissionModifiedEvent event = PermissionModifiedEvent.builder()
                .refId(this.getId().id())
                .permissionId(this.getId())
                .groupId(this.getGroupId())
                .permissionName(this.getPermissionName())
                .displayName(this.getDisplayName())
                .description(this.getDescription())
                .build();
        this.registerEvent(event);
    }

    /**
     * 删除权限
     */
    public void delete() {
        if (this.getDeleted()) {
            throw new BadRequestException("权限已被删除，无法重复删除，权限ID：" + this.getId());
        }

        this.setDeleted(true);

        PermissionDeletedEvent event = PermissionDeletedEvent.builder()
                .refId(this.getId().id())
                .permissionId(this.getId())
                .build();
        this.registerEvent(event);
    }
}
