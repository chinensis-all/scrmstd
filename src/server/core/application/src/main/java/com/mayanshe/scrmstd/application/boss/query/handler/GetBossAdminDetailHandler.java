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
package com.mayanshe.scrmstd.application.boss.query.handler;

import com.mayanshe.scrmstd.application.QueryHandler;
import com.mayanshe.scrmstd.application.boss.query.BossAdminDetailQuery;
import com.mayanshe.scrmstd.application.boss.query.dto.BossAdminDto;
import com.mayanshe.scrmstd.application.boss.query.repo.BossAdminQueryRepository;
import com.mayanshe.scrmstd.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetBossAdminDetailHandler implements QueryHandler<BossAdminDetailQuery, BossAdminDto> {
    private final BossAdminQueryRepository repository;

    public GetBossAdminDetailHandler(BossAdminQueryRepository repository) {
        this.repository = repository;
    }

    @Override
    public BossAdminDto handle(BossAdminDetailQuery query) {
        return repository.single(query.id())
                .orElseThrow(() -> new ResourceNotFoundException("超级管理员不存在"));
    }
}
