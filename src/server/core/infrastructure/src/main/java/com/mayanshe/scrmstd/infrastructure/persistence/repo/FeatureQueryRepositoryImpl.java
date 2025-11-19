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
import com.mayanshe.scrmstd.application.tenant.query.repo.FeatureQueryRepository;
import com.mayanshe.scrmstd.application.tenant.query.dto.FeatureDto;
import com.mayanshe.scrmstd.infrastructure.external.converter.FeatureConverter;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.FeatureMapper;
import com.mayanshe.scrmstd.infrastructure.persistence.po.FeaturePo;
import com.mayanshe.scrmstd.infrastructure.support.Pager;
import com.mayanshe.scrmstd.shared.model.Pagination;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * FeatureQueryRepositoryImpl: Saas功能点查询仓储实现
 */
@Repository
public class FeatureQueryRepositoryImpl implements FeatureQueryRepository {
    private final FeatureMapper mapper;

    public FeatureQueryRepositoryImpl(FeatureMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Optional<FeatureDto> single(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }

        FeaturePo po = mapper.findById(id);
        if (po == null) {
            return Optional.empty();
        }

        FeatureDto dto = FeatureConverter.INSTANCE.toDto(po);
        return Optional.of(dto);
    }

    @Override
    public List<OptionDto> search(Map<String, Object> criteria, long limit) {
        if (!criteria.containsKey("limit")) {
            criteria.put("limit", limit);
        }
        return mapper.search(criteria).stream().map(po -> new OptionDto(String.valueOf(po.getId()), po.getFeatureName())).toList();
    }

    @Override
    public Pagination<FeatureDto> paginate(Map<String, Object> criteria, long page, long size) {
        if (!criteria.containsKey("offset")) {
            criteria.put("offset", (page - 1) * size);
        }
        if (!criteria.containsKey("limit")) {
            criteria.put("limit", size);
        }
        return Pager.paginate(mapper, criteria, FeatureConverter.INSTANCE::toDto, page, size);
    }
}
