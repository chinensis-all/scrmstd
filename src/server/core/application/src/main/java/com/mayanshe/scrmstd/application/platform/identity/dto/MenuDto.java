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
package com.mayanshe.scrmstd.application.platform.identity.dto;

import cn.hutool.core.date.DateUtil;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import lombok.*;

/**
 * MenuDto: 菜单查询DTO
 */
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {
    private String id;
    private String parentId;
    private Byte kind;
    private String name;
    private String title;
    private String path;
    private String redirect;
    private String component;
    private String icon;
    private Integer sort;
    private Byte isExternal;
    private String externalLink;
    private Byte keepAlive;
    private Byte hideInMenu;
    private Byte hideChildrenInMenu;
    private Byte requiresAuth;
    private String permission;
    private Byte status;
    private String remark;
    private String createdAt;
    private String updatedAt;

    public String id() {
        return id;
    }

    public String parentId() {
        return parentId;
    }

    public String createdAt() {
        return createdAt == null || createdAt.isBlank() || "0".equals(createdAt) ? "" : DateUtil.format(DateUtil.date(Long.parseLong(createdAt) * 1000), "yyyy-MM-dd HH:mm:ss");
    }

    public String updatedAt() {
        return updatedAt == null || updatedAt.isBlank() || "0".equals(updatedAt) ? "" : DateUtil.format(DateUtil.date(Long.parseLong(updatedAt) * 1000), "yyyy-MM-dd HH:mm:ss");
    }

    public void setId(Long id) {
        this.id = IdGenerator.toBase62(id);
    }

    public void setParentId(Long parentId) {
        this.parentId = IdGenerator.toBase62(parentId);
    }
}
