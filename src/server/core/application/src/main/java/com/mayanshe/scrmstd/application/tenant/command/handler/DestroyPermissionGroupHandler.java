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

import com.mayanshe.scrmstd.application.tenant.command.DestroyPermissionGroupCommand;
import com.mayanshe.scrmstd.shared.contract.CommandHandler;
import com.mayanshe.scrmstd.shared.exception.NotFoundException;
import com.mayanshe.scrmstd.tenant.identity.model.PermissionGroup;
import com.mayanshe.scrmstd.tenant.identity.repo.PermissionGroupRepository;
import org.springframework.stereotype.Service;

/**
 * DestroyPermissionGroupHandler: 禁用权限组命令处理器
 */
@Service
public class DestroyPermissionGroupHandler implements CommandHandler<DestroyPermissionGroupCommand, Boolean> {
    private final PermissionGroupRepository permissionGroupRepository;

    public DestroyPermissionGroupHandler(PermissionGroupRepository permissionGroupRepository) {
        this.permissionGroupRepository = permissionGroupRepository;
    }

    @Override
    public Boolean handle(DestroyPermissionGroupCommand command) {
        PermissionGroup permissionGroup = permissionGroupRepository.load(command.id())
                .orElseThrow(() -> new NotFoundException("权限组不存在，ID：" + command.id()));
        permissionGroup.destroy();
        permissionGroupRepository.save(permissionGroup);
        return true;
    }
}
