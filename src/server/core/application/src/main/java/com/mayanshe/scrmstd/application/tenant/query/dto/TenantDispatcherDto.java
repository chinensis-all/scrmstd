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
package com.mayanshe.scrmstd.application.tentant.query.dto;


import cn.hutool.core.date.DateUtil;

/**
 * TenantDispatcher: 租户分发器DTO
 *
 * @param id              分发器ID
 * @param tenantId        租户ID
 * @param dispatcherType  分发器类型
 * @param dispatcherName  分发器名称
 * @param dispatcherClass 分发器类
 * @param entityClass     实体类
 * @param createdAt       创建时间
 * @param updatedAt       更新时间
 */
public record TenantDispatcherDto(
        String id,
        String tenantId,
        String dispatcherType,
        String dispatcherName,

        String dispatcherClass,
        String entityClass,
        String createdAt,
        String updatedAt
) {
    @Override
    public String createdAt() {
        return String.format(String.format(createdAt == null || createdAt.isBlank() ? "" : DateUtil.format(DateUtil.date(Long.parseLong(createdAt)), "yyyy-MM-dd HH:mm:ss")));
    }

    @Override
    public String updatedAt() {
        return String.format(String.format(updatedAt == null || updatedAt.isBlank() ? "" : DateUtil.format(DateUtil.date(Long.parseLong(updatedAt)), "yyyy-MM-dd HH:mm:ss")));
    }
}
