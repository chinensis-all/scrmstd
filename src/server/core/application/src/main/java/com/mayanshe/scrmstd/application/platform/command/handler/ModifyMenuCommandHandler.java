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
import com.mayanshe.scrmstd.application.platform.command.ModifyMenuCommand;
import com.mayanshe.scrmstd.platform.identity.model.Menu;
import com.mayanshe.scrmstd.platform.identity.repo.MenuRepository;
import com.mayanshe.scrmstd.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * ModifyMenuCommandHandler: 修改菜单命令处理器
 */
@Service
@RequiredArgsConstructor
public class ModifyMenuCommandHandler implements CommandHandler<ModifyMenuCommand, Boolean> {

    private final MenuRepository repository;

    private final DomainEventPublisher publisher;

    @Override
    public Boolean handle(ModifyMenuCommand command) {
        Menu menu = repository.load(command.id()).orElseThrow(() -> new ResourceNotFoundException("未找到此菜单: " + command.id()));

        menu.setParentId(command.parentId());
        menu.setKind(command.kind());
        menu.setName(command.name());
        menu.setTitle(command.title());
        menu.setPath(command.path());
        menu.setRedirect(command.redirect());
        menu.setComponent(command.component());
        menu.setIcon(command.icon());
        menu.setSort(command.sort());
        menu.setIsExternal(command.isExternal());
        menu.setExternalLink(command.externalLink());
        menu.setKeepAlive(command.keepAlive());
        menu.setHideInMenu(command.hideInMenu());
        menu.setHideChildrenInMenu(command.hideChildrenInMenu());
        menu.setRequiresAuth(command.requiresAuth());
        menu.setPermission(command.permission());
        menu.setStatus(command.status());
        menu.setRemark(command.remark());
        menu.modify();

        repository.save(menu);
        publisher.confirm(menu.getEvents());
        
        return true;
    }
}
