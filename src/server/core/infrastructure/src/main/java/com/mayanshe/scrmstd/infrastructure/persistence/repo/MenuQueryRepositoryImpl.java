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
package com.mayanshe.scrmstd.infrastructure.persistence.repo;

import com.mayanshe.scrmstd.application.OptionDto;
import com.mayanshe.scrmstd.application.platform.query.dto.MenuDto;
import com.mayanshe.scrmstd.application.platform.query.repo.MenuQueryRepository;
import com.mayanshe.scrmstd.infrastructure.external.converter.MenuConverter;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.MenuMapper;
import com.mayanshe.scrmstd.infrastructure.support.Pager;
import com.mayanshe.scrmstd.shared.model.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * MenuQueryRepositoryImpl: 菜单查询仓储接口实现
 */
@Repository
public class MenuQueryRepositoryImpl implements MenuQueryRepository {
    private final MenuMapper mapper;

    public MenuQueryRepositoryImpl(MenuMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Optional<MenuDto> single(Long key) {
        if (key == null || key <= 0) {
            return Optional.empty();
        }

        MenuDto dto = MenuConverter.INSTANCE.toDto(mapper.findById(key));
        return Optional.ofNullable(dto);
    }

    @Override
    public List<OptionDto> search(Map<String, Object> criteria, long limit) {
        if (!criteria.containsKey("limit")) {
            criteria.put("limit", limit);
        }
        return mapper.search(criteria).stream().map(po -> new OptionDto(String.valueOf(po.getId()), String.format("%s(%s)", po.getName(), po.getRemark()))).toList();
    }

    @Override
    public Pagination<MenuDto> paginate(Map<String, Object> criteria, long page, long size) {
        if (!criteria.containsKey("offset")) {
            criteria.put("offset", (page - 1) * size);
        }
        if (!criteria.containsKey("limit")) {
            criteria.put("limit", size);
        }
        return Pager.paginate(mapper, criteria, MenuConverter.INSTANCE::toDto, page, size);
    }
}
