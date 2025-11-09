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

import com.mayanshe.scrmstd.infrastructure.persistence.po.PermissionGroupPo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

import com.mayanshe.scrmstd.infrastructure.persistence.po.PermissionGroupPo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

@Mapper
public interface PermissionGroupMapper extends PaginateMapper<PermissionGroupPo> {
    @Insert("INSERT INTO permission_groups (id, group_name, display_name, description, created_at) VALUES (#{id}, #{groupName}, #{displayName}, #{description}, #{createdAt})")
    long insert(PermissionGroupPo po);

    @Update("UPDATE permission_groups SET group_name = #{groupName}, display_name = #{displayName}, description = #{description}, updated_at = #{updatedAt}, deleted_at = #{deletedAt} WHERE id = #{id}")
    long update(PermissionGroupPo po);

    @Update("UPDATE permission_groups SET deleted_at = UNIX_TIMESTAMP(now(3)) * 1000 WHERE id = #{id} AND deleted_at = 0")
    Long delete(Long id);

    @Select("SELECT * FROM permission_groups WHERE id = #{id} LIMIT 1")
    PermissionGroupPo findById(Long id);

    Long findIdByCondition(Map<String, Object> criteria);
}
