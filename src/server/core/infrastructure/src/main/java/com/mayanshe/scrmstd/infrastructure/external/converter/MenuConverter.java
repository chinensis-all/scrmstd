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
import com.mayanshe.scrmstd.application.platform.query.dto.MenuDto;
import com.mayanshe.scrmstd.infrastructure.persistence.po.MenuPo;
import com.mayanshe.scrmstd.platform.identity.model.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * MenuConverter: 菜单对象转换器
 */
@Mapper
public interface MenuConverter extends BaseConverter {
    MenuConverter INSTANCE = Mappers.getMapper(MenuConverter.class);

    @Mappings({
            @Mapping(target = "id", source = "id", qualifiedByName = "aggregateIdToId"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "isExternal", source = "isExternal", qualifiedByName = "booleanToByte"),
            @Mapping(target = "keepAlive", source = "keepAlive", qualifiedByName = "booleanToByte"),
            @Mapping(target = "hideInMenu", source = "hideInMenu", qualifiedByName = "booleanToByte"),
            @Mapping(target = "hideChildrenInMenu", source = "hideChildrenInMenu", qualifiedByName = "booleanToByte"),
            @Mapping(target = "requiresAuth", source = "requiresAuth", qualifiedByName = "booleanToByte")
    })
    MenuPo toPo(Menu aggregate);

    @Mappings({
            @Mapping(target = "id", source = "id", qualifiedByName = "aggregateIdToId"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "isExternal", source = "isExternal", qualifiedByName = "booleanToByte"),
            @Mapping(target = "keepAlive", source = "keepAlive", qualifiedByName = "booleanToByte"),
            @Mapping(target = "hideInMenu", source = "hideInMenu", qualifiedByName = "booleanToByte"),
            @Mapping(target = "hideChildrenInMenu", source = "hideChildrenInMenu", qualifiedByName = "booleanToByte"),
            @Mapping(target = "requiresAuth", source = "requiresAuth", qualifiedByName = "booleanToByte")
    })
    MenuPo updatePo(Menu aggregate, @MappingTarget MenuPo po);

    @Mappings({
            @Mapping(target = "id", source = "id", qualifiedByName = "idToAggregateId"),
            @Mapping(target = "isExternal", source = "isExternal", qualifiedByName = "byteToBoolean"),
            @Mapping(target = "keepAlive", source = "keepAlive", qualifiedByName = "byteToBoolean"),
            @Mapping(target = "hideInMenu", source = "hideInMenu", qualifiedByName = "byteToBoolean"),
            @Mapping(target = "hideChildrenInMenu", source = "hideChildrenInMenu", qualifiedByName = "byteToBoolean"),
            @Mapping(target = "requiresAuth", source = "requiresAuth", qualifiedByName = "byteToBoolean"),
            @Mapping(target = "deleted", ignore = true),
    })
    Menu toAggregate(MenuPo po);

    @Mappings({
            @Mapping(target = "id", expression = "java(String.valueOf(po.getId()))"),
            @Mapping(target = "parentId", expression = "java(String.valueOf(po.getParentId()))"),
            @Mapping(target = "isExternal", source = "isExternal", qualifiedByName = "byteToBoolean"),
            @Mapping(target = "keepAlive", source = "keepAlive", qualifiedByName = "byteToBoolean"),
            @Mapping(target = "hideInMenu", source = "hideInMenu", qualifiedByName = "byteToBoolean"),
            @Mapping(target = "hideChildrenInMenu", source = "hideChildrenInMenu", qualifiedByName = "byteToBoolean"),
            @Mapping(target = "requiresAuth", source = "requiresAuth", qualifiedByName = "byteToBoolean")
    })
    MenuDto toDto(MenuPo po);

    OptionDto toOptionDto(MenuPo po);
}
