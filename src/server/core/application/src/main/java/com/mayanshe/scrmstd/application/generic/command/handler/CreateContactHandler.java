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
package com.mayanshe.scrmstd.application.generic.command.handler;

import com.mayanshe.scrmstd.application.CommandHandler;
import com.mayanshe.scrmstd.application.generic.command.CreateContactCommand;
import com.mayanshe.scrmstd.generic.support.model.Contact;
import com.mayanshe.scrmstd.generic.support.repo.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateContactHandler implements CommandHandler<CreateContactCommand, Boolean> {
    private final ContactRepository repository;

    public CreateContactHandler(ContactRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Boolean handle(CreateContactCommand command) {
        repository.save(
                Contact.builder()
                        .id(command.id())
                        .identity(command.identity())
                        .fullName(command.fullName())
                        .email(command.email())
                        .phone(command.phone())
                        .build()
        );

        return true;
    }
}
