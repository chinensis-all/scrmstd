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

import com.mayanshe.scrmstd.infrastructure.persistence.po.RolePo;
import com.mayanshe.scrmstd.tenant.identity.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleConverter extends BaseConverter {
    RoleConverter INSTANCE = Mappers.getMapper(RoleConverter.class);

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "id", source = "id", qualifiedByName = "aggregateIdToId"),
    })
    RolePo toPo(Role role);

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "id", source = "id", qualifiedByName = "aggregateIdToId"),
    })
    RolePo updatePo(Role role, @MappingTarget RolePo po);

    @Mappings({
            @Mapping(target = "deleted", ignore = true),
            @Mapping(target = "id", source = "id", qualifiedByName = "idToAggregateId"),
    })
    Role toAggregate(RolePo po);
}
