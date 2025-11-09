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
package com.mayanshe.scrmstd.application.tentant.command.impl;

import com.mayanshe.scrmstd.application.CommandHandler;
import com.mayanshe.scrmstd.application.DomainEventPublisher;
import com.mayanshe.scrmstd.application.tentant.command.DestroyPermissionGroupCommand;
import com.mayanshe.scrmstd.tenant.identity.model.PermissionGroup;
import com.mayanshe.scrmstd.tenant.identity.repo.PermissionGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DestroyPermissionGroupHandler implements CommandHandler<DestroyPermissionGroupCommand, Boolean> {
    private final DomainEventPublisher publisher;
    private final PermissionGroupRepository repository;

    public DestroyPermissionGroupHandler(DomainEventPublisher publisher, PermissionGroupRepository repository) {
        this.publisher = publisher;
        this.repository = repository;
    }

    @Override
    @Transactional
    public Boolean handle(DestroyPermissionGroupCommand command) {
        PermissionGroup aggregate = repository.load(command.id()).orElseThrow(() -> new IllegalArgumentException("权限组不存在, ID=" + command.id()));

        aggregate.destroy();

        repository.save(aggregate);
        publisher.confirm(aggregate.getEvents());

        return true;
    }
}
