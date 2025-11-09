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
package com.mayanshe.scrmstd.application.message.command.handler;

import com.mayanshe.scrmstd.application.CommandHandler;
import com.mayanshe.scrmstd.application.DomainEventPublisher;
import com.mayanshe.scrmstd.application.message.command.CreateEmailLogCommand;
import com.mayanshe.scrmstd.message.log.model.EmailLog;
import com.mayanshe.scrmstd.message.log.repo.EmailLogRepository;
import com.mayanshe.scrmstd.message.log.type.EmailSendStatus;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import org.springframework.stereotype.Service;

@Service
public class CreateEmailLogHandler implements CommandHandler<CreateEmailLogCommand, Boolean> {
    private final EmailLogRepository repository;

    private final DomainEventPublisher publisher;

    private final IdGenerator idGenerator;

    public CreateEmailLogHandler(
            EmailLogRepository repository,
            DomainEventPublisher publisher,
            IdGenerator idGenerator
    ) {
        this.repository = repository;
        this.publisher = publisher;
        this.idGenerator = idGenerator;
    }

    @Override
    public Boolean handle(CreateEmailLogCommand command) {
        long id = idGenerator.nextId();

        EmailLog aggregate = EmailLog.builder()
                .id(new AggregateId(id, true))
                .tenantId(command.tenantId())
                .taskId(command.taskId())
                .templateId(command.templateId())
                .sender(command.sender())
                .recipient(command.recipient())
                .cc(command.cc())
                .subject(command.subject())
                .body(command.body())
                .params(command.params())
                .attachments(command.attachments())
                .status(EmailSendStatus.valueOf(command.status()))
                .build();

        repository.save(aggregate);
        publisher.confirm(aggregate.getEvents());

        return true;
    }
}
