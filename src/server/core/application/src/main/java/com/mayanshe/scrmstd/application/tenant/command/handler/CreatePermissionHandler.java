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
package com.mayanshe.scrmstd.application.tenant.command.handler;

import com.mayanshe.scrmstd.application.CommandHandler;
import com.mayanshe.scrmstd.application.DomainEventPublisher;
import com.mayanshe.scrmstd.application.tenant.command.CreatePermissionCommand;
import com.mayanshe.scrmstd.domain.tenant.identity.model.Permission;
import com.mayanshe.scrmstd.domain.tenant.identity.repo.PermissionRepository;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import com.mayanshe.scrmstd.shared.model.IDResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: 张西海
 * @date: 2025-11-16
 * @version: 1.0
 * @description: CreatePermissionHandler: 创建权限命令处理器
 */
@Service
public class CreatePermissionHandler implements CommandHandler<CreatePermissionCommand, IDResponse> {
    private final DomainEventPublisher publisher;
    private final PermissionRepository repository;

    public CreatePermissionHandler(DomainEventPublisher publisher, PermissionRepository repository) {
        this.publisher = publisher;
        this.repository = repository;
    }

    @Override
    @Transactional
    public IDResponse handle(CreatePermissionCommand command) {
        AggregateId id = repository.nextId();

        Permission aggregate = new Permission();
        aggregate.setId(id.toBuilder().newed(true).build());
        aggregate.setGroupId(command.groupId());
        aggregate.setPermissionName(command.permissionName());
        aggregate.setDisplayName(command.displayName());
        aggregate.setDescription(command.description());
        aggregate.create();

        repository.save(aggregate);
        publisher.confirm(aggregate.getEvents());

        return new IDResponse(id.id());
    }
}
