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
import com.mayanshe.scrmstd.application.platform.query.dto.FeatureDto;
import com.mayanshe.scrmstd.tenant.subscription.model.Feature;
import com.mayanshe.scrmstd.infrastructure.persistence.po.FeaturePo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * FeatureConverter: 功能信息转换器
 */
@Mapper
public interface FeatureConverter extends BaseConverter {
    FeatureConverter INSTANCE = Mappers.getMapper(FeatureConverter.class);

    @Mappings({
            @Mapping(target = "id", source = "id", qualifiedByName = "aggregateIdToId"),
            @Mapping(target = "configurable", source = "configurable", qualifiedByName = "booleanToByte"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "deletedAt", source = "deleted", qualifiedByName = "deletedToDeletedAt"),
    })
    FeaturePo toPo(Feature aggregate);

    @Mappings({
            @Mapping(target = "id", source = "id", qualifiedByName = "aggregateIdToId"),
            @Mapping(target = "configurable", source = "configurable", qualifiedByName = "booleanToByte"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "deletedAt", source = "deleted", qualifiedByName = "deletedToDeletedAt"),
    })
    FeaturePo updatePo(@MappingTarget FeaturePo po, Feature aggregate);

    @Mappings({
            @Mapping(target = "id", source = "id", qualifiedByName = "idToAggregateId"),
            @Mapping(target = "configurable", source = "configurable", qualifiedByName = "byteToBoolean"),
            @Mapping(target = "deleted", source = "deletedAt", qualifiedByName = "deletedAtToDeleted"),
    })
    Feature toAggregate(FeaturePo po);

    @Mappings({
            @Mapping(target = "id", expression = "java(String.valueOf(po.getId()))"),
            @Mapping(target = "configurable", source = "configurable", qualifiedByName = "byteToBoolean")
    })
    FeatureDto toDto(FeaturePo po);

    @Mappings({
            @Mapping(target = "id", expression = "java(String.valueOf(po.getId()))"),
            @Mapping(target = "name", source = "displayName")
    })
    OptionDto toOptionDto(FeaturePo po);
}
