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

import com.mayanshe.scrmstd.application.generic.query.dto.RegionDto;
import com.mayanshe.scrmstd.application.generic.query.repo.RegionQueryRepository;
import com.mayanshe.scrmstd.infrastructure.external.converter.RegionConverter;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.RegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class RegionQueryRepositoryImpl implements RegionQueryRepository {
    private final RegionMapper mapper;

    @Autowired
    public RegionQueryRepositoryImpl(RegionMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<RegionDto> search(Map<String, Object> criteria) {
        return mapper.search(criteria).stream()
                .map(RegionConverter.INSTANCE::toDto)
                .toList();
    }
}
