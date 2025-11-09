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

import com.mayanshe.scrmstd.application.generic.query.dto.RegionDto;
import com.mayanshe.scrmstd.generic.location.type.RegionLevel;
import com.mayanshe.scrmstd.infrastructure.persistence.po.RegionPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RegionConverter {
    RegionConverter INSTANCE = Mappers.getMapper(RegionConverter.class);

    @Mappings(
            @Mapping(target = "level", source = "regionLevel", qualifiedByName = "regionLevelToType")
    )
    RegionDto toDto(RegionPo po);

    @Named("regionLevelToType")
    default RegionLevel regionLevelToType(Short regionLevel) {
        return RegionLevel.of(String.valueOf(regionLevel));
    }
}
