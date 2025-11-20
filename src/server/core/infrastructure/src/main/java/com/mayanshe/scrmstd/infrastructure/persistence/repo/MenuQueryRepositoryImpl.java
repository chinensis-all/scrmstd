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
import com.mayanshe.scrmstd.application.platform.identity.dto.MenuDto;
import com.mayanshe.scrmstd.application.platform.identity.repo.MenuQueryRepository;
import com.mayanshe.scrmstd.infrastructure.external.converter.MenuConverter;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.MenuMapper;
import com.mayanshe.scrmstd.infrastructure.persistence.po.MenuPo;
import com.mayanshe.scrmstd.shared.model.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * MenuQueryRepositoryImpl: 菜单查询仓储接口实现
 */
@Repository
@RequiredArgsConstructor
public class MenuQueryRepositoryImpl implements MenuQueryRepository {

    private final MenuMapper menuMapper;
    private final MenuConverter menuConverter;

    @Override
    public MenuDto find(Long id) {
        MenuPo po = menuMapper.findById(id);
        if (po == null) {
            return null;
        }
        return menuConverter.toDto(po);
    }

    @Override
    public Pagination<MenuDto> paginate(int page, int size, Long parentId, Byte kind, String keywords, Byte status) {
        Map<String, Object> params = new HashMap<>();
        params.put("parentId", parentId);
        params.put("kind", kind);
        params.put("keywords", keywords);
        params.put("status", status);

        long total = menuMapper.count(params);
        if (total == 0) {
            return Pagination.empty(page, size);
        }

        params.put("offset", (page - 1) * size);
        params.put("limit", size);

        List<MenuDto> list = menuMapper.search(params).stream()
                .map(menuConverter::toDto)
                .collect(Collectors.toList());

        return Pagination.of(page, size, total, list);
    }

    @Override
    public List<OptionDto> options(Long parentId, Byte kind, String keywords, Byte status) {
        Map<String, Object> params = new HashMap<>();
        params.put("parentId", parentId);
        params.put("kind", kind);
        params.put("keywords", keywords);
        params.put("status", status);

        return menuMapper.search(params).stream()
                .map(menuConverter::toOptionDto)
                .collect(Collectors.toList());
    }
}
