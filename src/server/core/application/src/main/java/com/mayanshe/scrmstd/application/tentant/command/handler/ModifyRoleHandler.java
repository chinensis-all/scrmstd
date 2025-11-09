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
package com.mayanshe.scrmstd.application.tentant.command.handler;

import com.mayanshe.scrmstd.application.CommandHandler;
import com.mayanshe.scrmstd.application.DomainEventPublisher;
import com.mayanshe.scrmstd.application.tentant.command.ModifyRoleCommand;
import com.mayanshe.scrmstd.shared.model.IDResponse;
import com.mayanshe.scrmstd.tenant.identity.model.Role;
import com.mayanshe.scrmstd.tenant.identity.repo.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModifyRoleHandler implements CommandHandler<ModifyRoleCommand, IDResponse> {
    private final DomainEventPublisher publisher;

    private final RoleRepository repository;

    public ModifyRoleHandler(DomainEventPublisher publisher, RoleRepository repository) {
        this.publisher = publisher;
        this.repository = repository;
    }

    @Override
    @Transactional
    public IDResponse handle(ModifyRoleCommand command) {
        Role aggregate = repository.load(command.id()).orElseThrow(() -> new IllegalArgumentException("角色不存在, ID=" + command.id()));

        aggregate.modify(command.roleName(), command.description());

        repository.save(aggregate);
        publisher.confirm(aggregate.getEvents());

        return new IDResponse(String.valueOf(command.id()));
    }
}
