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
import com.mayanshe.scrmstd.application.message.command.CreateEmailTemplateCommand;
import com.mayanshe.scrmstd.message.template.model.EmailTemplate;
import com.mayanshe.scrmstd.message.template.repo.EmailTemplateRepository;
import com.mayanshe.scrmstd.message.template.type.EmailTemplateCategory;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 命令处理器：创建邮件模板
 */
@Service
public class CreateEmailTemplateHandler implements CommandHandler<CreateEmailTemplateCommand, Long> {
    private final IdGenerator idGenerator;
    private final EmailTemplateRepository repository;
    private final DomainEventPublisher publisher;

    public CreateEmailTemplateHandler(IdGenerator idGenerator, EmailTemplateRepository repository, DomainEventPublisher publisher) {
        this.idGenerator = idGenerator;
        this.repository = repository;
        this.publisher = publisher;
    }

    @Override
    @Transactional
    public Long handle(CreateEmailTemplateCommand command) {
        long id = idGenerator.nextId();

        EmailTemplate template = EmailTemplate.builder()
                .id(new AggregateId(id, true))
                .tenantId(command.tenantId() != null ? command.tenantId() : 0L)
                .templateName(command.templateName())
                .category(EmailTemplateCategory.fromCode(command.category()))
                .subject(command.subject())
                .body(command.body())
                .placeholders(command.placeholders())
                .build();

        template.create();

        repository.save(template);
        publisher.confirm(template.getEvents());

        return id;
    }
}
