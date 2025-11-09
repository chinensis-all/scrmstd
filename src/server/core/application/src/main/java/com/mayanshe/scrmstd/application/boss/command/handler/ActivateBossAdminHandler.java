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
package com.mayanshe.scrmstd.application.boss.command.handler;

import com.mayanshe.scrmstd.application.CommandHandler;
import com.mayanshe.scrmstd.application.DomainEventPublisher;
import com.mayanshe.scrmstd.application.boss.command.ActivateBossAdminCommand;
import com.mayanshe.scrmstd.domain.boss.admin.repo.BossAdminRepository;
import com.mayanshe.scrmstd.shared.model.IDResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActivateBossAdminHandler implements CommandHandler<ActivateBossAdminCommand, Boolean> {
    private final BossAdminRepository repository;

    private final DomainEventPublisher publisher;

    public ActivateBossAdminHandler(BossAdminRepository repository, DomainEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Override
    @Transactional
    public Boolean handle(ActivateBossAdminCommand command) {
        var aggregate = repository.load(command.id())
                .orElseThrow(() -> new IllegalArgumentException("管理员不存在 ID=" + command.id()));
        aggregate.activate();

        repository.save(aggregate);
        publisher.confirm(aggregate.getEvents());

        return true;
    }
}
