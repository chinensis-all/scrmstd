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
import com.mayanshe.scrmstd.application.message.command.DeleteEmailTemplateCommand;
import com.mayanshe.scrmstd.message.template.model.EmailTemplate;
import com.mayanshe.scrmstd.message.template.repo.EmailTemplateRepository;
import com.mayanshe.scrmstd.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 命令处理器：删除邮件模板
 */
@Service
public class DeleteEmailTemplateHandler implements CommandHandler<DeleteEmailTemplateCommand, Void> {
    private final EmailTemplateRepository repository;
    private final DomainEventPublisher publisher;

    public DeleteEmailTemplateHandler(EmailTemplateRepository repository, DomainEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Override
    @Transactional
    public Void handle(DeleteEmailTemplateCommand command) {
        EmailTemplate template = repository.load(command.templateId())
                .orElseThrow(() -> new ResourceNotFoundException("邮件模板不存在"));

        template.delete();

        repository.save(template);
        publisher.confirm(template.getEvents());

        return null;
    }
}
