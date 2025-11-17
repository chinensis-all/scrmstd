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
package com.mayanshe.scrmstd.application.tenant.query.dto;

import cn.hutool.core.date.DateUtil;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;

public record PermissionGroupDto(
        Long id,
        Long parentId,
        String groupName,
        String displayName,
        String description,
        Long createdAt,
        Long updatedAt,
        Long deletedAt
) {
    public String id() {
        return IdGenerator.toBase62(id);
    }

    public String parentId() {
        return IdGenerator.toBase62(parentId);
    }

    public String createdAt() {
        return createdAt == null || createdAt == 0 ? "" : DateUtil.format(DateUtil.date(createdAt), "yyyy-MM-dd HH:mm:ss");
    }

    public String updatedAt() {
        return updatedAt == null || updatedAt == 0 ? "" : DateUtil.format(DateUtil.date(updatedAt), "yyyy-MM-dd HH:mm:ss");
    }

    public String deletedAt() {
        return deletedAt == null || deletedAt == 0 ? "" : DateUtil.format(DateUtil.date(deletedAt), "yyyy-MM-dd HH:mm:ss");
    }
}
