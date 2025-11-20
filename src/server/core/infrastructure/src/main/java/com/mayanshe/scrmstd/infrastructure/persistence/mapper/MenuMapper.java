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

import com.mayanshe.scrmstd.infrastructure.persistence.po.MenuPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * MenuMapper: 菜单Mapper接口
 */
@Mapper
public interface MenuMapper extends PaginateMapper<MenuPo> {
    long insert(MenuPo po);

    long update(MenuPo po);

    Long delete(Long id);

    @Select("SELECT * FROM menus WHERE id = #{id} LIMIT 1")
    MenuPo findById(Long id);

    Long findIdByCondition(Map<String, Object> criteria);
}
