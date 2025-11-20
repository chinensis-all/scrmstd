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
import com.mayanshe.scrmstd.application.platform.query.dto.PermissionDto;
import com.mayanshe.scrmstd.application.platform.query.repo.PermissionQueryRepository;
import com.mayanshe.scrmstd.infrastructure.external.converter.PermissionConverter;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.PermissionMapper;
import com.mayanshe.scrmstd.infrastructure.support.Pager;
import com.mayanshe.scrmstd.shared.model.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * PermissionQueryRepositoryImpl: 权限查询仓储实现
 */
@Repository
public class PermissionQueryRepositoryImpl implements PermissionQueryRepository {
    private final PermissionMapper mapper;
    private final PermissionConverter converter;

    public PermissionQueryRepositoryImpl(PermissionMapper mapper, PermissionConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }

    /**
     * 获取单个权限
     *
     * @param id 主键
     * @return   权限DTO
     */
    @Override
    public Optional<PermissionDto> single(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }

        PermissionDto dto = converter.toDto(mapper.findById(id));

        return Optional.ofNullable(dto);
    }

    /**
     * 搜索权限选项列表
     */
    @Override
    public List<OptionDto> search(Map<String, Object> criteria, long limit) {
        if (!criteria.containsKey("limit")) {
            criteria.put("limit", limit);
        }
        return mapper.search(criteria).stream()
                .map(converter::toOptionDto)
                .toList();
    }

    /*
     * 分页查询权限
     */
    @Override
    public Pagination<PermissionDto> paginate(Map<String, Object> criteria, long page, long size) {
        if (!criteria.containsKey("offset")) {
            criteria.put("offset", (page - 1) * size);
        }
        if (!criteria.containsKey("limit")) {
            criteria.put("limit", size);
        }
        return Pager.paginate(mapper, criteria, converter::toDto, page, size);
    }
}
