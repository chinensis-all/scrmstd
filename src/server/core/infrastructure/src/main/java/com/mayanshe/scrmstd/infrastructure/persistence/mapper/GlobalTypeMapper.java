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
package com.mayanshe.scrmstd.infrastructure.persistence.mapper;

import com.mayanshe.scrmstd.shared.model.AggregateId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * GlobalTypeMapper: 全局类型映射器，处理通用类型转换
 */
@Mapper(componentModel = "spring")
public interface GlobalTypeMapper {
    @Mapping(target = "id", source = "id")
    default Long map(AggregateId id) {
        return id != null ? id.id() : null;
    }

    @Mapping(target = "id", source = "id")
    default AggregateId map(Long id) {
        return id != null ? new AggregateId(id, false) : null;
    }
}
