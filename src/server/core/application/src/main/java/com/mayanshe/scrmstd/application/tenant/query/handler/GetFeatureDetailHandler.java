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
package com.mayanshe.scrmstd.application.tenant.query.handler;

import com.mayanshe.scrmstd.application.QueryHandler;
import com.mayanshe.scrmstd.application.tenant.query.repo.FeatureQueryRepository;
import com.mayanshe.scrmstd.application.tenant.query.FeatureDetailQuery;
import com.mayanshe.scrmstd.application.tenant.query.dto.FeatureDto;
import com.mayanshe.scrmstd.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

/**
 * GetFeatureDetailHandler: 获取Saas功能点详情查询处理器
 */
@Component
public class GetFeatureDetailHandler implements QueryHandler<FeatureDetailQuery, FeatureDto> {
    private final FeatureQueryRepository featureQueryRepository;

    public GetFeatureDetailHandler(FeatureQueryRepository featureQueryRepository) {
        this.featureQueryRepository = featureQueryRepository;
    }

    @Override
    public FeatureDto handle(FeatureDetailQuery query) {
        return featureQueryRepository.single(query.id()).orElseThrow(() -> new ResourceNotFoundException("Saas功能点不存在：" + query.id()));
    }
}
