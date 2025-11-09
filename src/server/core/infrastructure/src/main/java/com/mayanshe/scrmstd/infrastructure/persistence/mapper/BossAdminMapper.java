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

import com.mayanshe.scrmstd.application.boss.query.dto.BossAdminDto;
import com.mayanshe.scrmstd.infrastructure.persistence.po.BossAdminPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * BossAdminMapper: Boss管理员
 */
@Mapper
public interface BossAdminMapper extends PaginateMapper<BossAdminPo> {
    long insert(BossAdminPo po);

    long update(BossAdminPo po);

    Long delete(Long id);

    BossAdminPo findById(Long id);

    @Select("SELECT * FROM _boss_admins WHERE username = #{username} AND deleted_at = 0 LIMIT 1")
    BossAdminPo findByUsername(String username);

    @Select("SELECT * FROM _boss_admins WHERE phone = #{phone} AND deleted_at = 0 LIMIT 1")
    BossAdminPo findByPhone(String phone);

    @Select("SELECT * FROM _boss_admins WHERE email = #{email} AND deleted_at = 0 LIMIT 1")
    BossAdminPo findByEmail(String email);

    Long findIdBy(Map<String, Object> params);

    Boolean exists(Map<String, Object> params);

    @Select("SELECT id, username, full_name, email, phone, avatar, last_login_at, last_login_ip, created_at, updated_at, deleted_at FROM _boss_admins WHERE id = #{id} AND deleted_at = 0")
    BossAdminDto selectById(Long id);
}
