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
import com.mayanshe.scrmstd.application.tenant.command.CreateAdminCommand;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import com.mayanshe.scrmstd.shared.model.IDResponse;
import com.mayanshe.scrmstd.tenant.identity.model.Admin;
import com.mayanshe.scrmstd.tenant.identity.repo.AdminRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CreateAdminHandler: 创建租户管理员命令处理器
 */
@Service
public class CreateAdminHandler implements CommandHandler<CreateAdminCommand, IDResponse> {
    private final IdGenerator idGenerator;

    private final DomainEventPublisher publisher;

    private final AdminRepository repository;

    public CreateAdminHandler(
            IdGenerator idGenerator,
            DomainEventPublisher publisher,
            AdminRepository repository
    ) {
        this.idGenerator = idGenerator;
        this.publisher = publisher;
        this.repository = repository;
    }

    @Override
    @Transactional
    public IDResponse handle(CreateAdminCommand command) {
        long id = idGenerator.nextId();

        Admin aggregate = Admin.builder()
                .id(new AggregateId(id, true))
                .tenantId(command.tenantId())
                .username(command.username())
                .password(command.password())
                .fullName(command.fullName())
                .email(command.email())
                .phone(command.phone())
                .avatar(command.avatar())
                .roleIds(command.roleIds())
                .build();
        aggregate.create();

        repository.save(aggregate);
        publisher.confirm(aggregate.getEvents());

        return new IDResponse(String.valueOf(id));
    }
}
