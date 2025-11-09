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
import com.mayanshe.scrmstd.application.boss.command.CreateBossAdminCommand;
import com.mayanshe.scrmstd.domain.boss.admin.model.BossAdmin;
import com.mayanshe.scrmstd.domain.boss.admin.repo.BossAdminRepository;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import com.mayanshe.scrmstd.shared.model.IDResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 命令处理器 : 创建管理员
 */
@Service
public class CreateBossAdminHandler implements CommandHandler<CreateBossAdminCommand, Long> {
    private final IdGenerator idGenerator;

    private final BossAdminRepository repository;

    private final DomainEventPublisher publisher;

    public CreateBossAdminHandler(IdGenerator idGenerator, BossAdminRepository repository, DomainEventPublisher publisher) {
        this.idGenerator = idGenerator;
        this.repository = repository;
        this.publisher = publisher;
    }

    @Override
    @Transactional
    public Long handle(CreateBossAdminCommand command) {
        long id = idGenerator.nextId();

        BossAdmin aggregate = BossAdmin.builder()
                .id(new AggregateId(id, true))
                .username(command.username().trim().toLowerCase())
                .fullName(command.fullName())
                .phone(command.phone())
                .email(command.email())
                .avatar(command.avatar())
                .lastLoginAt(0L)
                .lastLoginIp("")
                .build();
        aggregate.setSourcePassword(command.password());
        aggregate.create();

        repository.save(aggregate);
        publisher.confirm(aggregate.getEvents());

        return id;
    }
}
