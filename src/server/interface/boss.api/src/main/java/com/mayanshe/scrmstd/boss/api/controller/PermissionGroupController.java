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
package com.mayanshe.scrmstd.boss.api.controller;

import com.mayanshe.scrmstd.application.IDResponse;
import com.mayanshe.scrmstd.application.OptionDto;
import com.mayanshe.scrmstd.application.tenant.command.ActivatePermissionGroupCommand;
import com.mayanshe.scrmstd.application.tenant.command.CreatePermissionGroupCommand;
import com.mayanshe.scrmstd.application.tenant.command.DestroyPermissionGroupCommand;
import com.mayanshe.scrmstd.application.tenant.command.ModifyPermissionGroupCommand;
import com.mayanshe.scrmstd.application.tenant.query.PermissionGroupDetailQuery;
import com.mayanshe.scrmstd.application.tenant.query.PermissionGroupOptionQuery;
import com.mayanshe.scrmstd.application.tenant.query.PermissionGroupPaginationQuery;
import com.mayanshe.scrmstd.application.tenant.query.dto.PermissionGroupDto;
import com.mayanshe.scrmstd.boss.api.request.CreatePermissionGroupRequest;
import com.mayanshe.scrmstd.boss.api.request.ModifyPermissionGroupRequest;
import com.mayanshe.scrmstd.shared.bus.CommandBus;
import com.mayanshe.scrmstd.shared.bus.QueryBus;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import com.mayanshe.scrmstd.shared.model.Pagination;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PermissionGroupController: 权限组控制器
 */
@RestController
@RequestMapping("/v1/permission-groups")
public class PermissionGroupController {
    @Resource(name = "simpleCommandBus")
    private CommandBus commandBus;

    @Resource(name = "simpleQueryBus")
    private QueryBus queryBus;

    @PostMapping
    public IDResponse createPermissionGroup(@RequestBody CreatePermissionGroupRequest request) {
        CreatePermissionGroupCommand command = new CreatePermissionGroupCommand(
                IdGenerator.fromBase62(request.parentId()),
                request.groupName(),
                request.displayName(),
                request.description()
        );
        return commandBus.dispatch(command);
    }

    @PutMapping("/{id}")
    public void modifyPermissionGroup(@PathVariable String id, @RequestBody ModifyPermissionGroupRequest request) {
        ModifyPermissionGroupCommand command = new ModifyPermissionGroupCommand(
                IdGenerator.fromBase62(id),
                IdGenerator.fromBase62(request.parentId()),
                request.groupName(),
                request.displayName(),
                request.description()
        );
        commandBus.dispatch(command);
    }

    @PostMapping("/{id}/lock")
    public void destroyPermissionGroup(@PathVariable String id) {
        DestroyPermissionGroupCommand command = new DestroyPermissionGroupCommand(IdGenerator.fromBase62(id));
        commandBus.dispatch(command);
    }

    @DeleteMapping("/{id}/lock")
    public void activatePermissionGroup(@PathVariable String id) {
        ActivatePermissionGroupCommand command = new ActivatePermissionGroupCommand(IdGenerator.fromBase62(id));
        commandBus.dispatch(command);
    }

    @GetMapping("/{id}")
    public PermissionGroupDto getPermissionGroupDetail(@PathVariable String id) {
        PermissionGroupDetailQuery query = new PermissionGroupDetailQuery(IdGenerator.fromBase62(id));
        return queryBus.dispatch(query);
    }

    @GetMapping("/options")
    public List<OptionDto> getPermissionGroupOption() {
        PermissionGroupOptionQuery query = new PermissionGroupOptionQuery();
        return queryBus.dispatch(query);
    }

    @GetMapping
    public Pagination<PermissionGroupDto> getPermissionGroupPagination(@RequestParam(defaultValue = "1") int page,
                                                                         @RequestParam(defaultValue = "10") int pageSize) {
        PermissionGroupPaginationQuery query = new PermissionGroupPaginationQuery(page, pageSize);
        return queryBus.dispatch(query);
    }
}
