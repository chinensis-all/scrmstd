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
import com.mayanshe.scrmstd.application.platform.query.PermissionPaginationQuery;
import com.mayanshe.scrmstd.application.platform.query.dto.PermissionDto;
import com.mayanshe.scrmstd.application.platform.query.repo.PermissionQueryRepository;
import com.mayanshe.scrmstd.shared.model.Pagination;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * PermissionPaginationQueryHandler: 获取权限分页查询处理器
 */
@Service
public class PermissionPaginationQueryHandler implements QueryHandler<PermissionPaginationQuery, Pagination<PermissionDto>> {
    private final PermissionQueryRepository repository;

    public PermissionPaginationQueryHandler(PermissionQueryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pagination<PermissionDto> handle(PermissionPaginationQuery query) {
        Map<String, Object> criteria = new HashMap<>();
        if (query.groupId() != null && query.groupId() > 0) {
            criteria.put("groupId", query.groupId());
        }
        if (query.keywords() != null && !query.keywords().isBlank()) {
            criteria.put("keywords", query.keywords());
        }

        return repository.paginate(criteria, query.page(), query.pageSize());
    }
}
