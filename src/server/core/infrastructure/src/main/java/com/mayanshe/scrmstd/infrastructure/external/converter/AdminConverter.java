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

import com.mayanshe.scrmstd.infrastructure.persistence.po.AdminPo;
import com.mayanshe.scrmstd.tenant.identity.model.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdminConverter extends BaseConverter {
    AdminConverter INSTANCE = Mappers.getMapper(AdminConverter.class);

    @Mappings({
            @Mapping(target = "id", source = "id", qualifiedByName = "aggregateIdToId"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "deletedAt", source = "deleted", qualifiedByName = "deletedToDeletedAt"),
    })
    AdminPo toPo(Admin aggregate);

    @Mappings({
            @Mapping(target = "id", source = "id", qualifiedByName = "aggregateIdToId"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "deletedAt", source = "deleted", qualifiedByName = "deletedToDeletedAt"),
    })
    AdminPo updatePo(Admin aggregate, @MappingTarget AdminPo po);

    @Mappings({
            @Mapping(target = "id", source = "id", qualifiedByName = "idToAggregateId"),
            @Mapping(target = "deleted", source = "deletedAt", qualifiedByName = "deletedAtToDeleted"),
    })
    Admin toAggregate(AdminPo po);
}
