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
import com.mayanshe.scrmstd.application.platform.query.repo.FeatureQueryRepository;
import com.mayanshe.scrmstd.application.platform.query.FeatureOptionQuery;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * GetFeatureOptionHandler: 获取Sass功能点选项处理器
 */
@Component
public class GetFeatureOptionHandler implements QueryHandler<FeatureOptionQuery, List<OptionDto>> {
    private final FeatureQueryRepository repository;

    public GetFeatureOptionHandler(FeatureQueryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<OptionDto> handle(FeatureOptionQuery query) {
        return repository.search(query.toMap(), 200);
    }
}
