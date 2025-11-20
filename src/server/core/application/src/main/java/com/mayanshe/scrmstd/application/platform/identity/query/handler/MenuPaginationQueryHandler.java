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
package com.mayanshe.scrmstd.application.platform.identity.query.handler;

import com.mayanshe.scrmstd.application.QueryHandler;
import com.mayanshe.scrmstd.application.platform.identity.dto.MenuDto;
import com.mayanshe.scrmstd.application.platform.identity.query.MenuPaginationQuery;
import com.mayanshe.scrmstd.application.platform.identity.repo.MenuQueryRepository;
import com.mayanshe.scrmstd.shared.model.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * MenuPaginationQueryHandler: 菜单分页查询处理器
 */
@Service
@RequiredArgsConstructor
public class MenuPaginationQueryHandler implements QueryHandler<MenuPaginationQuery, Pagination<MenuDto>> {

    private final MenuQueryRepository menuQueryRepository;

    @Override
    public Pagination<MenuDto> handle(MenuPaginationQuery query) {
        return menuQueryRepository.paginate(
                query.getPage(),
                query.getSize(),
                query.getParentId(),
                query.getKind(),
                query.getKeywords(),
                query.getStatus()
        );
    }
}
