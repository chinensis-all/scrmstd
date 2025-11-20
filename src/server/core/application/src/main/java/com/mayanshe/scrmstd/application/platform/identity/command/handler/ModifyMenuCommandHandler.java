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
package com.mayanshe.scrmstd.application.platform.identity.command.handler;

import com.mayanshe.scrmstd.application.CommandHandler;
import com.mayanshe.scrmstd.application.DomainEventPublisher;
import com.mayanshe.scrmstd.application.platform.identity.command.ModifyMenuCommand;
import com.mayanshe.scrmstd.platform.identity.model.Menu;
import com.mayanshe.scrmstd.platform.identity.repo.MenuRepository;
import com.mayanshe.scrmstd.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ModifyMenuCommandHandler: 修改菜单命令处理器
 */
@Service
@RequiredArgsConstructor
public class ModifyMenuCommandHandler implements CommandHandler<ModifyMenuCommand, Boolean> {

    private final MenuRepository menuRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean handle(ModifyMenuCommand command) {
        Menu menu = menuRepository.find(command.getId());
        if (menu == null) {
            throw new NotFoundException("菜单不存在");
        }

        menu.modify(
                command.getParentId(),
                command.getKind(),
                command.getName(),
                command.getTitle(),
                command.getPath(),
                command.getRedirect(),
                command.getComponent(),
                command.getIcon(),
                command.getSort(),
                command.getIsExternal(),
                command.getExternalLink(),
                command.getKeepAlive(),
                command.getHideInMenu(),
                command.getHideChildrenInMenu(),
                command.getRequiresAuth(),
                command.getPermission(),
                command.getStatus(),
                command.getRemark()
        );

        menuRepository.save(menu);
        domainEventPublisher.publish(menu);
        return true;
    }
}
