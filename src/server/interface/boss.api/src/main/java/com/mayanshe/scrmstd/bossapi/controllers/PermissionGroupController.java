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
package com.mayanshe.scrmstd.bossapi.controllers;

import com.mayanshe.scrmstd.application.CommandBus;
import com.mayanshe.scrmstd.application.QueryBus;
import com.mayanshe.scrmstd.application.tentant.command.ActivatePermissionGroupCommand;
import com.mayanshe.scrmstd.application.tentant.command.CreatePermissionGroupCommand;
import com.mayanshe.scrmstd.application.tentant.command.DestroyPermissionGroupCommand;
import com.mayanshe.scrmstd.application.tentant.command.ModifyPermissionGroupCommand;
import com.mayanshe.scrmstd.application.tentant.query.PermissionGroupDetailQuery;
import com.mayanshe.scrmstd.application.tentant.query.PermissionGroupOptionLQuery;
import com.mayanshe.scrmstd.application.tentant.query.PermissionGroupPaginationQuery;
import com.mayanshe.scrmstd.bossapi.requests.CreatePermissionGroupRequest;
import com.mayanshe.scrmstd.bossapi.requests.ModifyPermissionGroupRequest;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import com.mayanshe.scrmstd.shared.model.IDResponse;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permission-groups")
public class PermissionGroupController {
    @Resource(name = "simpleCommandBus")
    private CommandBus commandBus;

    @Resource(name = "simpleQueryBus")
    private QueryBus queryBus;

    @PostMapping
    public IDResponse createPermissionGroup(@Valid @RequestBody CreatePermissionGroupRequest request) {
        CreatePermissionGroupCommand command = new CreatePermissionGroupCommand(request.getGroupName(), request.getDisplayName(), request.getDescription());
        return commandBus.execute(command);
    }

    @PutMapping("/{id}")
    public void modifyPermissionGroup(@PathVariable("id") String id, @Valid @RequestBody ModifyPermissionGroupRequest request) {
        ModifyPermissionGroupCommand command = new ModifyPermissionGroupCommand(IdGenerator.fromBase62(id), request.getGroupName(), request.getDisplayName(), request.getDescription());
        commandBus.execute(command);
    }

    @PostMapping("/{id}/lock")
    public void destroyPermissionGroup(@PathVariable("id") String id) {
        DestroyPermissionGroupCommand command = new DestroyPermissionGroupCommand(IdGenerator.fromBase62(id));
        commandBus.execute(command);
    }

    @DeleteMapping("/{id}/lock")
    public void activatePermissionGroup(@PathVariable("id") String id) {
        ActivatePermissionGroupCommand command = new ActivatePermissionGroupCommand(IdGenerator.fromBase62(id));
        commandBus.execute(command);
    }

    @GetMapping("/{id}")
    public Object getPermissionGroupDetail(@PathVariable("id") String id) {
        return queryBus.execute(new PermissionGroupDetailQuery(IdGenerator.fromBase62(id)));
    }

    @GetMapping("/options")
    public Object getPermissionGroupOptions(@RequestParam(value = "keywords", required = false) String keywords) {
        return queryBus.execute(new PermissionGroupOptionLQuery(keywords));
    }

    @GetMapping
    public Object getPermissionGroupPagination(@RequestParam(value = "page", required = false) Integer page,
                                               @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return queryBus.execute(new PermissionGroupPaginationQuery(page, pageSize));
    }
}
