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

import com.mayanshe.scrmstd.platform.identity.event.MenuCreatedEvent;
import com.mayanshe.scrmstd.platform.identity.event.MenuDeletedEvent;
import com.mayanshe.scrmstd.platform.identity.event.MenuModifiedEvent;
import com.mayanshe.scrmstd.shared.base.Aggregate;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import lombok.*;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Menu: 菜单聚合根
 */
@Getter
@Setter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends Aggregate {
    private AggregateId id;

    private Long parentId;

    private Byte kind;

    private String name;

    private String title;

    private String path;

    private String redirect;

    private String component;

    private String icon;

    private Integer sort;

    private Boolean isExternal;

    private String externalLink;

    private Boolean keepAlive;

    private Boolean hideInMenu;

    private Boolean hideChildrenInMenu;

    private Boolean requiresAuth;

    private String permission;

    private Byte status;

    private String remark;

    @Builder.Default
    public boolean deleted = false;

    public void create() {
        this.setDeleted(false);

        MenuCreatedEvent event = MenuCreatedEvent.builder()
                .menuId(id)
                .parentId(parentId)
                .kind(kind)
                .name(name)
                .title(title)
                .path(path)
                .redirect(redirect)
                .component(component)
                .icon(icon)
                .sort(sort)
                .isExternal(isExternal)
                .externalLink(externalLink)
                .keepAlive(keepAlive)
                .hideInMenu(hideInMenu)
                .hideChildrenInMenu(hideChildrenInMenu)
                .requiresAuth(requiresAuth)
                .permission(permission)
                .status(status)
                .remark(remark)
                .build();
        this.registerEvent(event);
    }

    public void modify() {
        this.setDeleted(false);

        MenuModifiedEvent event = MenuModifiedEvent.builder()
                .menuId(this.id)
                .parentId(parentId)
                .kind(kind)
                .name(name)
                .title(title)
                .path(path)
                .redirect(redirect)
                .component(component)
                .icon(icon)
                .sort(sort)
                .isExternal(isExternal)
                .externalLink(externalLink)
                .keepAlive(keepAlive)
                .hideInMenu(hideInMenu)
                .hideChildrenInMenu(hideChildrenInMenu)
                .requiresAuth(requiresAuth)
                .permission(permission)
                .status(status)
                .remark(remark)
                .build();
        this.registerEvent(event);
    }

    public void delete() {
        this.setDeleted(true);

        MenuDeletedEvent event = MenuDeletedEvent.builder()
                .menuId(this.id)
                .build();
        this.registerEvent(event);
    }
}
