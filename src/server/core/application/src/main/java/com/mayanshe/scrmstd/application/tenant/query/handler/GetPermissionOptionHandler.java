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

import com.mayanshe.scrmstd.application.OptionDto;
import com.mayanshe.scrmstd.application.QueryHandler;
import com.mayanshe.scrmstd.application.query.repo.PermissionQueryRepository;
import com.mayanshe.scrmstd.application.tenant.query.PermissionOptionQuery;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 张西海
 * @date: 2025-11-16
 * @version: 1.0
 * @description: GetPermissionOptionHandler: 获取权限选项查询处理器
 */
@Service
public class GetPermissionOptionHandler implements QueryHandler<PermissionOptionQuery, List<OptionDto>> {
    private final PermissionQueryRepository repository;

    public GetPermissionOptionHandler(PermissionQueryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<OptionDto> handle(PermissionOptionQuery query) {
        Map<String, Object> criteria = new HashMap<>();
        if (query.groupId() != null) {
            criteria.put("groupId", query.groupId());
        }
        if (query.keywords() != null && !query.keywords().isBlank()) {
            criteria.put("keywords", query.keywords());
        }

        return repository.search(criteria, 200);
    }
}
