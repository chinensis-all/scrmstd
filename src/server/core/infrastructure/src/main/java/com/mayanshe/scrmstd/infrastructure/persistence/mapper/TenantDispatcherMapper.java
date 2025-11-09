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

import com.mayanshe.scrmstd.infrastructure.persistence.po.TenantDispatcherPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * TenantApplicationHandlerMapper: 租户应用处理器Mapper
 */
@Mapper
public interface TenantDispatcherMapper extends PaginateMapper<TenantDispatcherPo> {
    int insert(TenantDispatcherPo po);

    int update(TenantDispatcherPo po);

    int delete(Long id);

    @Select("SELECT * FROM tenant_dispatchers WHERE id = #{id}")
    TenantDispatcherPo findById(Long id);

    Boolean exists(Map<String, Object> params);
}
