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
package com.mayanshe.scrmstd.tenant.management.type;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum TenantStatus {
    PENDING("待配置"), TRIAL("试用"), ACTIVE("启用"), INACTIVE("禁用"), SUSPENDED("暂停");

    private final String name;

    TenantStatus(String name) {
        this.name = name;
    }

    public static String getNameByCode(String code) {
        for (TenantStatus value : values()) {
            if (value.name().equalsIgnoreCase(code)) {
                return value.getName();
            }
        }
        return null;
    }

    public static List<Map<String, String>> all() {
        return Arrays.stream(values()).map(v -> Map.of("code", v.name(), "name", v.getName())).collect(Collectors.toList());
    }

    public static TenantStatus of(String code) {
        for (TenantStatus value : values()) {
            if (value.name().equalsIgnoreCase(code)) {
                return value;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
