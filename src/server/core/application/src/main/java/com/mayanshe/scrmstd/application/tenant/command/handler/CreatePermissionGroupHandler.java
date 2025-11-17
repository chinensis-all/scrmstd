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

import com.mayanshe.scrmstd.application.IDResponse;
import com.mayanshe.scrmstd.application.tenant.command.CreatePermissionGroupCommand;
import com.mayanshe.scrmstd.shared.contract.CommandHandler;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import com.mayanshe.scrmstd.tenant.identity.model.PermissionGroup;
import com.mayanshe.scrmstd.tenant.identity.repo.PermissionGroupRepository;
import org.springframework.stereotype.Service;

/**
 * CreatePermissionGroupHandler: 创建权限组命令处理器
 */
@Service
public class CreatePermissionGroupHandler implements CommandHandler<CreatePermissionGroupCommand, IDResponse> {
    private final PermissionGroupRepository permissionGroupRepository;

    public CreatePermissionGroupHandler(PermissionGroupRepository permissionGroupRepository) {
        this.permissionGroupRepository = permissionGroupRepository;
    }

    @Override
    public IDResponse handle(CreatePermissionGroupCommand command) {
        PermissionGroup permissionGroup = PermissionGroup.builder()
                .id(new AggregateId())
                .parentId(command.parentId())
                .groupName(command.groupName())
                .displayName(command.displayName())
                .description(command.description())
                .build();
        permissionGroup.create();
        permissionGroupRepository.save(permissionGroup);
        return new IDResponse(permissionGroup.getId().id());
    }
}
