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
package com.mayanshe.scrmstd.generic.location.type;

import java.util.List;

public class RegionLevel {
    public static final RegionLevel PROVINCE = new RegionLevel("1", "省");
    public static final RegionLevel CITY = new RegionLevel("2", "市");
    public static final RegionLevel DISTRICT = new RegionLevel("3", "区/县");
    public static final RegionLevel TOWN = new RegionLevel("4", "镇/街道");
    public static final RegionLevel VILLAGE = new RegionLevel("5", "村/社区");

    public static final List<RegionLevel> ALL_REGION_TYPES = List.of(PROVINCE, CITY, DISTRICT, TOWN, VILLAGE);

    private final String code;

    private final String name;

    public RegionLevel(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static List<RegionLevel> all() {
        return ALL_REGION_TYPES;
    }

    public static RegionLevel of(String code) {
        return ALL_REGION_TYPES.stream().filter(type -> type.getCode().equals(code)).findFirst().orElse(null);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
