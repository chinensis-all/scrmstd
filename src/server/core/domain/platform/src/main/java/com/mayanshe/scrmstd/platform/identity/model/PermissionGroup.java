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
import com.mayanshe.scrmstd.platform.identity.event.ActivatePermissionGroupEvent;
import com.mayanshe.scrmstd.platform.identity.event.CreatePermissionGroupEvent;
import com.mayanshe.scrmstd.platform.identity.event.DestroyPermissionGroupEvent;
import com.mayanshe.scrmstd.platform.identity.event.ModifyPermissionGroupEvent;
import lombok.*;

/**
 * PermissionGroup: 权限组聚合根
 */
@Getter
@Setter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class PermissionGroup extends Aggregate {
    private AggregateId id;

    private Long parentId;

    private String groupName;

    private String displayName;

    private String description;

    @Builder.Default
    private Boolean deleted = false;

    /**
     * 创建权限组
     */
    public void create() {
        this.setDeleted(false);

        CreatePermissionGroupEvent event = CreatePermissionGroupEvent.builder()
                .refId(this.getId().id())
                .permissionGroupId(this.getId())
                .groupName(this.getGroupName())
                .displayName(this.getDisplayName())
                .description(this.getDescription())
                .build();
        this.registerEvent(event);
    }

    /**
     * 修改权限组
     * @param parentId   上级权限组ID
     * @param groupName  权限组名称
     * @param displayName 显示名称
     * @param description 描述
     */
    public void modify(Long parentId, String groupName, String displayName, String description) {
        this.setGroupName(groupName);
        this.setDisplayName(displayName);
        this.setDescription(description);
        this.setDeleted(false);

        ModifyPermissionGroupEvent event = ModifyPermissionGroupEvent.builder()
                .refId(this.getId().id())
                .permissionGroupId(this.getId())
                .groupName(this.getGroupName())
                .displayName(this.getDisplayName())
                .description(this.getDescription())
                .build();
        this.registerEvent(event);
    }

    /**
     * 销毁权限组
     */
    public void destroy() {
        if (this.getDeleted()) {
            throw new BadRequestException("权限组已被禁用，无法重复禁用，权限组ID：" + this.getId());
        }

        this.setDeleted(true);

        DestroyPermissionGroupEvent event = DestroyPermissionGroupEvent.builder()
                .refId(this.getId().id())
                .permissionGroupId(this.getId())
                .build();
        this.registerEvent(event);
    }

    /**
     * 激活权限组
     */
    public void activate() {
        if (!this.getDeleted()) {
            throw new BadRequestException("权限组未被禁用，无法激活，权限组ID：" + this.getId());
        }

        this.setDeleted(false);

        ActivatePermissionGroupEvent event = ActivatePermissionGroupEvent.builder()
                .refId(this.getId().id())
                .permissionGroupId(this.getId())
                .build();
        this.registerEvent(event);
    }
}
