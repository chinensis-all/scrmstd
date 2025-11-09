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

import com.mayanshe.scrmstd.infrastructure.persistence.po.ContactPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ContactMapper {
    long insert(ContactPo po);

    @Select("SELECT * FROM _contacts WHERE id = #{id} LIMIT 1")
    ContactPo findById(Long id);

    @Select("SELECT count(1) > 0 FROM _contacts WHERE email = #{email} AND identity = #{identity} LIMIT 1")
    boolean existsByEmail(String email, String identity);

    @Select("SELECT count(1) > 0 FROM _contacts WHERE phone = #{phone} AND identity = #{identity} LIMIT 1")
    boolean existsByPhone(String phone, String identity);
}
