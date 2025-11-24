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
package com.mayanshe.scrmstd.application.platform.command.handler;

import com.mayanshe.scrmstd.application.CommandHandler;
import com.mayanshe.scrmstd.application.DomainEventPublisher;
import com.mayanshe.scrmstd.application.platform.command.DeleteMenuCommand;
import com.mayanshe.scrmstd.platform.identity.model.Menu;
import com.mayanshe.scrmstd.platform.identity.repo.MenuRepository;
import com.mayanshe.scrmstd.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

/**
 * DeleteMenuCommandHandler: 删除菜单命令处理器
 */
@Service
public class DeleteMenuCommandHandler implements CommandHandler<DeleteMenuCommand, Boolean> {
    private final MenuRepository repository;

    private final DomainEventPublisher publisher;

    public DeleteMenuCommandHandler(MenuRepository repository, DomainEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Override
    public Boolean handle(DeleteMenuCommand command) {
        Menu menu = repository.load(command.id()).orElseThrow(() -> new ResourceNotFoundException("未找到此菜单: " + command.id()));
        menu.delete();

        repository.save(menu);
        publisher.confirm(menu.getEvents());

        return true;
    }
}
