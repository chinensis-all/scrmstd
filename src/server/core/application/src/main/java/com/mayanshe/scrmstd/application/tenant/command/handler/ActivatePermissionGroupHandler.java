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

import com.mayanshe.scrmstd.application.tenant.command.ActivatePermissionGroupCommand;
import com.mayanshe.scrmstd.shared.contract.CommandHandler;
import com.mayanshe.scrmstd.shared.exception.NotFoundException;
import com.mayanshe.scrmstd.tenant.identity.model.PermissionGroup;
import com.mayanshe.scrmstd.tenant.identity.repo.PermissionGroupRepository;
import org.springframework.stereotype.Service;

/**
 * ActivatePermissionGroupHandler: 激活权限组命令处理器
 */
@Service
public class ActivatePermissionGroupHandler implements CommandHandler<ActivatePermissionGroupCommand, Boolean> {
    private final PermissionGroupRepository permissionGroupRepository;

    public ActivatePermissionGroupHandler(PermissionGroupRepository permissionGroupRepository) {
        this.permissionGroupRepository = permissionG<pre>
[
  {
    "thought": "Finally, `ActivatePermissionGroupHandler.java`."
  },
  {
    "tool_code": "create_file_with_block(filepath='src/server/core/application/src/main/java/com/mayanshe/scrmstd/application/tenant/command/handler/ActivatePermissionGroupHandler.java', content='''/*\n * [ScrmStd] - 通用SCRM系统\n * Copyright (C) [2025] [张西海]\n *\n * This program is free software: you can redistribute it and/or modify\n * it under the terms of the GNU Affero General Public License as published by\n * the Free Software Foundation, either version 3 of the License, or\n * (at your option) any later version.\n *\n * This program is distributed in the hope that it will be useful,\n * but WITHOUT ANY WARRANTY; without even the implied warranty of\n * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n * GNU Affero General Public License for more details.\n *\n * You should have received a copy of the GNU Affero General Public License\n * along with this program.  If not, see <https://www.gnu.org/licenses/>.\n */\npackage com.mayanshe.scrmstd.application.tenant.command.handler;\n\nimport com.mayanshe.scrmstd.application.tenant.command.ActivatePermissionGroupCommand;\nimport com.mayanshe.scrmstd.shared.contract.CommandHandler;\nimport com.mayanshe.scrmstd.shared.exception.NotFoundException;\nimport com.mayanshe.scrmstd.tenant.identity.model.PermissionGroup;\nimport com.mayanshe.scrmstd.tenant.identity.repo.PermissionGroupRepository;\nimport org.springframework.stereotype.Service;\n\n/**\n * ActivatePermissionGroupHandler: 激活权限组命令处理器\n */\n@Service\npublic class ActivatePermissionGroupHandler implements CommandHandler<ActivatePermissionGroupCommand, Boolean> {\n    private final PermissionGroupRepository permissionGroupRepository;\n\n    public ActivatePermissionGroupHandler(PermissionGroupRepository permissionGroupRepository) {\n        this.permissionGroupRepository = permissionGroupRepository;\n    }\n\n    @Override\n    public Boolean handle(ActivatePermissionGroupCommand command) {\n        PermissionGroup permissionGroup = permissionGroupRepository.load(command.id())\n                .orElseThrow(() -> new NotFoundException(\"权限组不存在，ID：\" + command.id()));\n        permissionGroup.activate();\n        permissionGroupRepository.save(permissionGroup);\n        return true;\n    }\n}\n''' )"
  }
]
</pre>roupRepository;
    }

    @Override
    public Boolean handle(ActivatePermissionGroupCommand command) {
        PermissionGroup permissionGroup = permissionGroupRepository.load(command.id())
                .orElseThrow(() -> new NotFoundException("权限组不存在，ID：" + command.id()));
        permissionGroup.activate();
        permissionGroupRepository.save(permissionGroup);
        return true;
    }
}
