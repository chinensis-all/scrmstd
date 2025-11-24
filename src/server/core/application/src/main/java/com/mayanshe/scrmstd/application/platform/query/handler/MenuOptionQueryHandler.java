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
package com.mayanshe.scrmstd.application.platform.query.handler;

import com.mayanshe.scrmstd.application.OptionDto;
import com.mayanshe.scrmstd.application.QueryHandler;
import com.mayanshe.scrmstd.application.platform.query.MenuOptionQuery;
import com.mayanshe.scrmstd.application.platform.query.repo.MenuQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MenuOptionQueryHandler: 菜单列表查询处理器
 */
@Service
public class MenuOptionQueryHandler implements QueryHandler<MenuOptionQuery, List<OptionDto>> {
    private final MenuQueryRepository repository;

    public MenuOptionQueryHandler(MenuQueryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<OptionDto> handle(MenuOptionQuery query) {
        return repository.search(query.toMap(), 200);
    }
}
