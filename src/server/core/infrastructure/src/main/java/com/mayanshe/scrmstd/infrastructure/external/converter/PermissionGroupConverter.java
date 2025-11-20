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
import com.mayanshe.scrmstd.application.platform.query.dto.PermissionGroupDto;
import com.mayanshe.scrmstd.infrastructure.persistence.po.PermissionGroupPo;
import com.mayanshe.scrmstd.platform.identity.model.PermissionGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface PermissionGroupConverter extends BaseConverter {
    PermissionGroupConverter INSTANCE = Mappers.getMapper(PermissionGroupConverter.class);

    @Mappings({
            @Mapping(target = "id", source = "id", qualifiedByName = "aggregateIdToId"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "deletedAt", source = "deleted", qualifiedByName = "deletedToDeletedAt"),
    })
    PermissionGroupPo toPo(PermissionGroup aggregate);

    @Mappings({
            @Mapping(target = "id", source = "id", qualifiedByName = "aggregateIdToId"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "deletedAt", source = "deleted", qualifiedByName = "deletedToDeletedAt"),
    })
    PermissionGroupPo updatePo(PermissionGroup aggregate, @MappingTarget PermissionGroupPo po);

    @Mappings({
            @Mapping(target = "id", source = "id", qualifiedByName = "idToAggregateId"),
            @Mapping(target = "deleted", source = "deletedAt", qualifiedByName = "deletedAtToDeleted"),
    })
    PermissionGroup toAggregate(PermissionGroupPo po);

    @Mapping(target = "id", expression = "java(String.valueOf(po.getId()))")
    PermissionGroupDto toDto(PermissionGroupPo po);

    @Mapping(target = "id", expression = "java(String.valueOf(po.getId()))")
    @Mapping(target = "name", source = "displayName")
    OptionDto toOptionDto(PermissionGroupPo po);
}
