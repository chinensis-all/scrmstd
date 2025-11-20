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
package com.mayanshe.scrmstd.application.platform.identity.repo;

import com.mayanshe.scrmstd.application.OptionDto;
import com.mayanshe.scrmstd.application.QueryRepository;
import com.mayanshe.scrmstd.application.platform.identity.dto.MenuDto;
import com.mayanshe.scrmstd.shared.model.Pagination;

import java.util.List;

/**
 * MenuQueryRepository: 菜单查询仓储接口
 */
public interface MenuQueryRepository extends QueryRepository<MenuDto> {
    Pagination<MenuDto> paginate(int page, int size, Long parentId, Byte kind, String keywords, Byte status);

    List<OptionDto> options(Long parentId, Byte kind, String keywords, Byte status);
}
