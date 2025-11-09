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

import com.mayanshe.scrmstd.application.boss.query.dto.BossAdminDto;
import com.mayanshe.scrmstd.domain.boss.admin.model.BossAdmin;

import com.mayanshe.scrmstd.infrastructure.persistence.po.BossAdminPo;
import com.mayanshe.scrmstd.shared.contract.CosClientFactory;
import com.mayanshe.scrmstd.shared.model.CosFileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * BossAdminConverter: Boss管理员转换器
 */
@Mapper(componentModel = "spring")
@Component
public abstract class BossAdminConverter implements BaseConverter {

    @Lazy
    @Autowired
    private CosClientFactory cosClientFactory;

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "deletedAt", source = "deleted", qualifiedByName = "deletedToDeletedAt"),
            @Mapping(target = "id", source = "id", qualifiedByName = "aggregateIdToId"),
    })
    public abstract BossAdminPo toPo(BossAdmin aggregate);

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "deletedAt", source = "deleted", qualifiedByName = "deletedToDeletedAt"),
            @Mapping(target = "id", source = "id", qualifiedByName = "aggregateIdToId"),
    })
    public abstract BossAdminPo updatePo(BossAdmin aggregate, @MappingTarget BossAdminPo po);

    @Mappings({
            @Mapping(target = "deleted", source = "deletedAt", qualifiedByName = "deletedAtToDeleted"),
            @Mapping(target = "id", source = "id", qualifiedByName = "idToAggregateId"),
    })
    public abstract BossAdmin toAggregate(BossAdminPo po);

    @Mappings({
            @Mapping(
                    target = "avatar",
                    expression = "java(pathToCosFileDto(po.getAvatar()))"
            ),
    })
    public abstract BossAdminDto toDto(BossAdminPo po);

    protected CosFileDto pathToCosFileDto(String path) {
        try {
            return cosClientFactory.getClient(null).getFileDto(path);
        } catch (IOException e) {
            return null;
        }
    }
}
