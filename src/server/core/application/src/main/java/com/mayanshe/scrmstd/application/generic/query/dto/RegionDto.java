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
package com.mayanshe.scrmstd.application.generic.query.dto;

import com.mayanshe.scrmstd.generic.location.type.RegionLevel;

import java.math.BigDecimal;

/**
 * RegionDto: 区域数据传输对象
 * @param id
 * @param parentId
 * @param level
 * @param postalCode
 * @param areaCode
 * @param regionName
 * @param shortName
 * @param mergeName
 * @param longitude
 * @param latitude
 */
public record RegionDto(
        String id,
        String parentId,
        RegionLevel level,
        String postalCode,
        String areaCode,
        String regionName,
        String shortName,
        String mergeName,
        BigDecimal longitude,
        BigDecimal latitude
) {}
