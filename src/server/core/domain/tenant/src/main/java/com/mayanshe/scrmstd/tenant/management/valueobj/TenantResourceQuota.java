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
package com.mayanshe.scrmstd.tenant.management.valueobj;

import com.mayanshe.scrmstd.tenant.management.type.DataIsolationLevel;

/**
 * ResourceQuota: 资源配额值对象
 *
 * @param maxUsers     最大用户数
 * @param maxStorageGb 最大存储空间（GB）
 */
public record TenantResourceQuota(
        int maxUsers,
        int maxStorageGb,
        DataIsolationLevel dataIsolationLevel
) {}
