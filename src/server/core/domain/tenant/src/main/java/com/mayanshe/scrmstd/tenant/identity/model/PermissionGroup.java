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
import com.mayanshe.scrmstd.tenant.identity.event.ActivatePermissionGroupEvent;
import com.mayanshe.scrmstd.tenant.identity.event.CreatePermissionGroupEvent;
import com.mayanshe.scrmstd.tenant.identity.event.DestroyPermissionGroupEvent;
import com.mayanshe.scrmstd.tenant.identity.event.ModifyPermissionGroupEvent;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class PermissionGroup extends Aggregate {
    private AggregateId id;

    private String groupName;

    private String displayName;

    private String description;

    @Builder.Default
    private Boolean deleted = false;

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

    public void modify() {
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

    public void destroy() {
        this.setDeleted(true);

        DestroyPermissionGroupEvent event = DestroyPermissionGroupEvent.builder()
                .refId(this.getId().id())
                .permissionGroupId(this.getId())
                .build();
        this.registerEvent(event);
    }

    public void activate() {
        this.setDeleted(false);

        ActivatePermissionGroupEvent event = ActivatePermissionGroupEvent.builder()
                .refId(this.getId().id())
                .permissionGroupId(this.getId())
                .build();
        this.registerEvent(event);
    }
}
