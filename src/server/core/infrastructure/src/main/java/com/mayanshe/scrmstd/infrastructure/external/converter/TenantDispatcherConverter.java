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

import com.mayanshe.scrmstd.infrastructure.config.GlobalMappingConfig;
import com.mayanshe.scrmstd.infrastructure.persistence.po.TenantDispatcherPo;
import com.mayanshe.scrmstd.tenant.configuration.model.TenantDispatcher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.lang.annotation.Target;

/**
 * TenantDispatcherConverter: 租户分发器转换器，负责在领域模型和持久化对象以及传输对象之间进行转换
 */
@Mapper(config = GlobalMappingConfig.class)
public interface TenantDispatcherConverter {
    TenantDispatcherConverter INSTANCE = Mappers.getMapper(TenantDispatcherConverter.class);

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
    })
    TenantDispatcherPo toPo(TenantDispatcher aggregate);

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
    })
    TenantDispatcherPo updatePo(TenantDispatcher aggregate, @MappingTarget TenantDispatcherPo po);

    @Mappings({
            @Mapping(target = "deleted", ignore = true),
    })
    TenantDispatcher toAggregate(TenantDispatcherPo po);
}
