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

/**
 * 订阅计划
 */
public enum SubscriptionPlan  {
    BASIC("基础"),
    STANDARD("标准"),
    PREMIUM("高级"),
    ENTERPRISE("企业"),
    CUSTOM("自定义"),
    NONE("无");

    private final String name;

    SubscriptionPlan(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(String code) {
        for (SubscriptionPlan value : values()) {
            if (value.name().equalsIgnoreCase(code)) {
                return value.getName();
            }
        }
        return null;
    }

    public static List<Map<String, String>> all() {
        return Arrays.stream(values())
                .map(v -> Map.of("code", v.name(), "name", v.getName()))
                .collect(Collectors.toList());
    }

    public static SubscriptionPlan of(String code) {
        for (SubscriptionPlan value : values()) {
            if (value.name().toUpperCase().equalsIgnoreCase(code)) {
                return value;
            }
        }
        return null;
    }
}
