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

import com.mayanshe.scrmstd.infrastructure.persistence.po.RolePo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper {
    long insert(RolePo po);

    long update(RolePo po);

    Long delete(Long id);

    @Select("SELECT * FROM roles WHERE id = #{id}")
    RolePo findById(Long id);

    @Select("SELECT id FROM roles WHERE tenant_id = #{tenantId} AND role_name = #{roleName} LIMIT 1")
    Long findIdByRoleName(@Param("tenantId") Long tenantId, @Param("roleName") String roleName);

    List<RolePo> findByIds(List<Long> ids);
}
