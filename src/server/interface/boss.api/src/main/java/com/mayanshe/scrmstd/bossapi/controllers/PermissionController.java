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
import com.mayanshe.scrmstd.application.tenant.command.CreatePermissionCommand;
import com.mayanshe.scrmstd.application.tenant.command.DeletePermissionCommand;
import com.mayanshe.scrmstd.application.tenant.command.ModifyPermissionCommand;
import com.mayanshe.scrmstd.application.tenant.query.PermissionDetailQuery;
import com.mayanshe.scrmstd.application.tenant.query.PermissionOptionQuery;
import com.mayanshe.scrmstd.application.tenant.query.PermissionPaginationQuery;
import com.mayanshe.scrmstd.bossapi.requests.CreatePermissionRequest;
import com.mayanshe.scrmstd.bossapi.requests.ModifyPermissionRequest;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import com.mayanshe.scrmstd.shared.model.IDResponse;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * PermissionController: 权限控制器
 */
@RestController
@RequestMapping("/permissions")
public class PermissionController {
    @Resource(name = "simpleCommandBus")
    private CommandBus commandBus;

    @Resource(name = "simpleQueryBus")
    private QueryBus queryBus;

    /**
     * 创建权限
     *
     * @param request 创建请求
     * @return        创建结果
     */
    @PostMapping
    public IDResponse createPermission(@Valid @RequestBody CreatePermissionRequest request) {
        CreatePermissionCommand command = new CreatePermissionCommand(
                IdGenerator.fromBase62(request.getGroupId()),
                request.getPermissionName(),
                request.getDisplayName(),
                request.getDescription()
        );

        long id = commandBus.execute(command);

        return new IDResponse(IdGenerator.toBase62(id));
    }

    /**
     * 修改权限
     *
     * @param id      权限ID
     * @param request 修改请求
     */
    @PutMapping("/{id}")
    public void modifyPermission(@PathVariable("id") String id, @Valid @RequestBody ModifyPermissionRequest request) {
        ModifyPermissionCommand command = new ModifyPermissionCommand(
                IdGenerator.fromBase62(id),
                IdGenerator.fromBase62(request.getGroupId()),
                request.getPermissionName(),
                request.getDisplayName(),
                request.getDescription()
        );
        commandBus.execute(command);
    }

    /**
     * 删除权限
     *
     * @param id 权限ID
     */
    @DeleteMapping("/{id}")
    public void deletePermission(@PathVariable("id") String id) {
        DeletePermissionCommand command = new DeletePermissionCommand(IdGenerator.fromBase62(id));
        commandBus.execute(command);
    }

    /**
     * 获取权限详情
     *
     * @param id 权限ID
     * @return   详情结果
     */
    @GetMapping("/{id}")
    public Object getPermissionDetail(@PathVariable("id") String id) {
        return queryBus.execute(new PermissionDetailQuery(IdGenerator.fromBase62(id)));
    }

    /**
     * 获取权限选项列表
     */
    @GetMapping("/options")
    public Object getPermissionOptions(
            @RequestParam(value = "groupId", required = false) String groupId,
            @RequestParam(value = "keywords", required = false) String keywords
    ) {
        Long decodedGroupId = (groupId == null || groupId.isBlank()) ? null : IdGenerator.fromBase62(groupId);
        return queryBus.execute(new PermissionOptionQuery(decodedGroupId, keywords));
    }

    /**
     * 获取权限分页列表
     */
    @GetMapping
    public Object getPermissionPagination(
            @RequestParam(value = "groupId", required = false) String groupId,
            @RequestParam(value = "keywords", required = false) String keywords,
            @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") Long pageSize
    ) {
        Long decodedGroupId = (groupId == null || groupId.isBlank()) ? null : IdGenerator.fromBase62(groupId);
        return queryBus.execute(new PermissionPaginationQuery(decodedGroupId, keywords, page, pageSize));
    }
}
