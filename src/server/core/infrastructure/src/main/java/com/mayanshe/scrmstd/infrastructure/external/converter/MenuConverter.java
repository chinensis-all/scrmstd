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
package com.mayanshe.scrmstd.infrastructure.external.converter;

import com.mayanshe.scrmstd.application.OptionDto;
import com.mayanshe.scrmstd.application.platform.identity.dto.MenuDto;
import com.mayanshe.scrmstd.infrastructure.persistence.po.MenuPo;
import com.mayanshe.scrmstd.platform.identity.model.Menu;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import org.springframework.stereotype.Component;

/**
 * MenuConverter: 菜单对象转换器
 */
@Component
public class MenuConverter {

    public MenuPo toPo(Menu menu) {
        return MenuPo.builder()
                .id(menu.getId())
                .parentId(menu.getParentId())
                .kind(menu.getKind())
                .name(menu.getName())
                .title(menu.getTitle())
                .path(menu.getPath())
                .redirect(menu.getRedirect())
                .component(menu.getComponent())
                .icon(menu.getIcon())
                .sort(menu.getSort())
                .isExternal(menu.getIsExternal())
                .externalLink(menu.getExternalLink())
                .keepAlive(menu.getKeepAlive())
                .hideInMenu(menu.getHideInMenu())
                .hideChildrenInMenu(menu.getHideChildrenInMenu())
                .requiresAuth(menu.getRequiresAuth())
                .permission(menu.getPermission())
                .status(menu.getStatus())
                .remark(menu.getRemark())
                .build();
    }

    public Menu toAggregate(MenuPo po) {
        return Menu.builder()
                .id(po.getId())
                .parentId(po.getParentId())
                .kind(po.getKind())
                .name(po.getName())
                .title(po.getTitle())
                .path(po.getPath())
                .redirect(po.getRedirect())
                .component(po.getComponent())
                .icon(po.getIcon())
                .sort(po.getSort())
                .isExternal(po.getIsExternal())
                .externalLink(po.getExternalLink())
                .keepAlive(po.getKeepAlive())
                .hideInMenu(po.getHideInMenu())
                .hideChildrenInMenu(po.getHideChildrenInMenu())
                .requiresAuth(po.getRequiresAuth())
                .permission(po.getPermission())
                .status(po.getStatus())
                .remark(po.getRemark())
                .build();
    }

    public MenuDto toDto(MenuPo po) {
        MenuDto dto = MenuDto.builder()
                .kind(po.getKind())
                .name(po.getName())
                .title(po.getTitle())
                .path(po.getPath())
                .redirect(po.getRedirect())
                .component(po.getComponent())
                .icon(po.getIcon())
                .sort(po.getSort())
                .isExternal(po.getIsExternal())
                .externalLink(po.getExternalLink())
                .keepAlive(po.getKeepAlive())
                .hideInMenu(po.getHideInMenu())
                .hideChildrenInMenu(po.getHideChildrenInMenu())
                .requiresAuth(po.getRequiresAuth())
                .permission(po.getPermission())
                .status(po.getStatus())
                .remark(po.getRemark())
                .createdAt(String.valueOf(po.getCreatedAt()))
                .updatedAt(String.valueOf(po.getUpdatedAt()))
                .build();
        dto.setId(po.getId());
        dto.setParentId(po.getParentId());
        return dto;
    }

    public OptionDto toOptionDto(MenuPo po) {
        return new OptionDto(String.valueOf(po.getId()), po.getTitle());
    }
}
