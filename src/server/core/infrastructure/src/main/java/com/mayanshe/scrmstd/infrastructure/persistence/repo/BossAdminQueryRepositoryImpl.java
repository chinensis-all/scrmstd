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
import com.mayanshe.scrmstd.application.boss.query.dto.BossAdminDto;
import com.mayanshe.scrmstd.application.boss.query.repo.BossAdminQueryRepository;
import com.mayanshe.scrmstd.infrastructure.external.converter.BossAdminConverter;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.BossAdminMapper;
import com.mayanshe.scrmstd.infrastructure.support.Pager;
import com.mayanshe.scrmstd.shared.model.Pagination;
import com.mayanshe.scrmstd.shared.util.PrintUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BossAdminQueryRepositoryImpl implements BossAdminQueryRepository {
    private final BossAdminMapper mapper;

    private final BossAdminConverter converter;

    public BossAdminQueryRepositoryImpl(BossAdminMapper mapper, BossAdminConverter converter) {
        this.mapper = mapper;
        this.converter = converter;
    }

    @Override
    public Optional<BossAdminDto> single(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }

        BossAdminDto dto = converter.toDto(mapper.findById(id));

        return Optional.ofNullable(dto);
    }

    @Override
    public List<OptionDto> search(Map<String, Object> criteria, long limit) {
        if (!criteria.containsKey("limit")) {
            criteria.put("limit", limit);
        }

        if (!criteria.containsKey("deleted")) {
            criteria.put("deleted", false);
        }

        PrintUtil.error("BossAdmin search criteria: {}", mapper.search(criteria));

        return mapper.search(criteria).stream().map(po -> new OptionDto(String.valueOf(po.getId()), po.getFullName())).toList();
    }

    @Override
    public Pagination<BossAdminDto> paginate(Map<String, Object> criteria, long page, long size) {
        if (!criteria.containsKey("offset")) {
            criteria.put("offset", (page - 1) * size);
        }
        if (!criteria.containsKey("limit")) {
            criteria.put("limit", size);
        }
        return Pager.paginate(mapper, criteria, converter::toDto, page, size);
    }
}
