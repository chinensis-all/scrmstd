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
import com.mayanshe.scrmstd.application.tenant.query.dto.PermissionDto;
import com.mayanshe.scrmstd.infrastructure.persistence.po.PermissionPo;
import com.mayanshe.scrmstd.tenant.identity.model.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

/**
 * PermissionConverter: 权限转换器
 */
@Mapper(componentModel = "spring")
@Component
public interface PermissionConverter extends BaseConverter {
    PermissionConverter INSTANCE = Mappers.getMapper(PermissionConverter.class);

    @Mappings({
            @Mapping(target = "id", source = "id", qualifiedByName = "aggregateIdToId"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
    })
    PermissionPo toPo(Permission aggregate);

    @Mappings({
            @Mapping(target = "id", source = "id", qualifiedByName = "aggregateIdToId"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
    })
    PermissionPo updatePo(Permission aggregate, @MappingTarget PermissionPo po);

    @Mappings({
            @Mapping(target = "id", source = "id", qualifiedByName = "idToAggregateId"),
    })
    Permission toAggregate(PermissionPo po);

    @Mapping(target = "id", expression = "java(String.valueOf(po.getId()))")
    @Mapping(target = "groupId", expression = "java(String.valueOf(po.getGroupId()))")
    PermissionDto toDto(PermissionPo po);

    @Mapping(target = "id", expression = "java(String.valueOf(po.getId()))")
    @Mapping(target = "name", source = "displayName")
    OptionDto toOptionDto(PermissionPo po);
}
