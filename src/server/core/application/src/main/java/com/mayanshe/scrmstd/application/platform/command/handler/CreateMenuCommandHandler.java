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
import com.mayanshe.scrmstd.application.platform.command.CreateMenuCommand;
import com.mayanshe.scrmstd.platform.identity.model.Menu;
import com.mayanshe.scrmstd.platform.identity.repo.MenuRepository;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CreateMenuCommandHandler: 创建菜单命令处理器
 */
@Service
public class CreateMenuCommandHandler implements CommandHandler<CreateMenuCommand, Long> {
    private final MenuRepository repository;
    
    private final DomainEventPublisher publisher;

    private final IdGenerator idGenerator;

    public CreateMenuCommandHandler(MenuRepository repository, DomainEventPublisher publisher, IdGenerator idGenerator) {
        this.repository = repository;
        this.publisher = publisher;
        this.idGenerator = idGenerator;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long handle(CreateMenuCommand command) {
        Long id = idGenerator.nextId();

        Menu aggregate = Menu.builder()
                .id(AggregateId.newed(id))
                .parentId(command.parentId())
                .kind(command.kind())
                .name(command.name())
                .title(command.title())
                .path(command.path())
                .redirect(command.redirect())
                .component(command.component())
                .icon(command.icon())
                .sort(command.sort())
                .isExternal(command.isExternal())
                .externalLink(command.externalLink())
                .keepAlive(command.keepAlive())
                .hideInMenu(command.hideInMenu())
                .hideChildrenInMenu(command.hideChildrenInMenu())
                .requiresAuth(command.requiresAuth())
                .permission(command.permission())
                .status(command.status())
                .remark(command.remark())
                .build();
        aggregate.create();

        repository.save(aggregate);
        publisher.confirm(aggregate.getEvents());

        return id;
    }
}
