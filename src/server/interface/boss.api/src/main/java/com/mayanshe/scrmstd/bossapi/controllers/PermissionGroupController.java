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
import com.mayanshe.scrmstd.application.tenant.command.ActivatePermissionGroupCommand;
import com.mayanshe.scrmstd.application.tenant.command.CreatePermissionGroupCommand;
import com.mayanshe.scrmstd.application.tenant.command.DestroyPermissionGroupCommand;
import com.mayanshe.scrmstd.application.tenant.command.ModifyPermissionGroupCommand;
import com.mayanshe.scrmstd.application.tenant.query.PermissionGroupDetailQuery;
import com.mayanshe.scrmstd.application.tenant.query.PermissionGroupOptionQuery;
import com.mayanshe.scrmstd.application.tenant.query.PermissionGroupPaginationQuery;
import com.mayanshe.scrmstd.bossapi.requests.CreatePermissionGroupRequest;
import com.mayanshe.scrmstd.bossapi.requests.ModifyPermissionGroupRequest;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import com.mayanshe.scrmstd.shared.model.IDResponse;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/*
 * PermissionGroupController: 权限组控制器
 */
@RestController
@RequestMapping("/permission-groups")
public class PermissionGroupController {
    @Resource(name = "simpleCommandBus")
    private CommandBus commandBus;

    @Resource(name = "simpleQueryBus")
    private QueryBus queryBus;

    /**
     * 创建权限组
     *
     * @param request 创建请求
     * @return        创建结果
     */
    @PostMapping
    public IDResponse createPermissionGroup(@Valid @RequestBody CreatePermissionGroupRequest request) {
        CreatePermissionGroupCommand command = new CreatePermissionGroupCommand(
                request.getParentId() == null || request.getParentId().isBlank() ? 0L : IdGenerator.fromBase62(request.getParentId()),
                request.getGroupName(),
                request.getDisplayName(),
                request.getDescription()
        );

        long id = commandBus.execute(command);

        return new IDResponse(IdGenerator.toBase62(id));
    }

    /**
     * 修改权限组
     *
     * @param id      权限组ID
     * @param request 修改请求
     */
    @PutMapping("/{id}")
    public void modifyPermissionGroup(@PathVariable("id") String id, @Valid @RequestBody ModifyPermissionGroupRequest request) {
        ModifyPermissionGroupCommand command = new ModifyPermissionGroupCommand(
                IdGenerator.fromBase62(id),
                request.getParentId() == null || request.getParentId().isBlank() ? 0L : IdGenerator.fromBase62(request.getParentId()),
                request.getGroupName(),
                request.getDisplayName(),
                request.getDescription()
        );
        commandBus.execute(command);
    }

    /**
     * 禁用权限组
     *
     * @param id 权限组ID
     */
    @PostMapping("/{id}/lock")
    public void destroyPermissionGroup(@PathVariable("id") String id) {
        DestroyPermissionGroupCommand command = new DestroyPermissionGroupCommand(IdGenerator.fromBase62(id));
        commandBus.execute(command);
    }

    /**
     * 启用权限组
     *
     * @param id 权限组ID
     */
    @DeleteMapping("/{id}/lock")
    public void activatePermissionGroup(@PathVariable("id") String id) {
        ActivatePermissionGroupCommand command = new ActivatePermissionGroupCommand(IdGenerator.fromBase62(id));
        commandBus.execute(command);
    }

    /**
     * 获取权限组详情
     *
     * @param id 权限组ID
     * @return   详情结果
     */
    @GetMapping("/{id}")
    public Object getPermissionGroupDetail(@PathVariable("id") String id) {
        return queryBus.execute(new PermissionGroupDetailQuery(IdGenerator.fromBase62(id)));
    }

    /*
     * 获取权限组选项列表
     */
    @GetMapping("/options")
    public Object getPermissionGroupOptions(
            @RequestParam(value = "parentId", required = false) Long parentId,
            @RequestParam(value = "keywords", required = false) String keywords
    ) {
        return queryBus.execute(new PermissionGroupOptionQuery(parentId, keywords));
    }

    /*
     * 获取权限组分页列表
     */
    @GetMapping
    public Object getPermissionGroupPagination(
            @RequestParam(value = "parentId", required = false) Long parentId,
            @RequestParam(value = "keywords", required = false) String keywords,
            @RequestParam(value = "deleted", required = false) Boolean deleted,
            @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") Long pageSize
    ) {
        return queryBus.execute(new PermissionGroupPaginationQuery(parentId, keywords, deleted, page, pageSize));
    }
}
