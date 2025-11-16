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
import com.mayanshe.scrmstd.application.tentant.command.CreatePermissionGroupCommand;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import com.mayanshe.scrmstd.shared.model.IDResponse;
import com.mayanshe.scrmstd.tenant.identity.model.PermissionGroup;
import com.mayanshe.scrmstd.tenant.identity.repo.PermissionGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CreatePermissionGroupHandler: 创建权限组命令处理器
 */
@Service
public class CreatePermissionGroupHandler implements CommandHandler<CreatePermissionGroupCommand, Long> {
    private final IdGenerator idGenerator;
    private final DomainEventPublisher publisher;
    private final PermissionGroupRepository repository;

    public CreatePermissionGroupHandler(IdGenerator idGenerator, DomainEventPublisher publisher, PermissionGroupRepository repository) {
        this.idGenerator = idGenerator;
        this.publisher = publisher;
        this.repository = repository;
    }

    @Override
    @Transactional
    public Long handle(CreatePermissionGroupCommand command) {
        long id = idGenerator.nextId();

        PermissionGroup aggregate = new PermissionGroup();
        aggregate.setId(new AggregateId(id, true));
        aggregate.setParentId(command.parentId());
        aggregate.setGroupName(command.groupName());
        aggregate.setDisplayName(command.displayName());
        aggregate.setDescription(command.description());
        aggregate.create();

        repository.save(aggregate);
        publisher.confirm(aggregate.getEvents());

        return id;
    }
}
