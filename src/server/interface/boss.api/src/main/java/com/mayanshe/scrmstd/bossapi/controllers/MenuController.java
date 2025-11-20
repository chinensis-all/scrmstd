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
import com.mayanshe.scrmstd.application.OptionDto;
import com.mayanshe.scrmstd.application.QueryBus;
import com.mayanshe.scrmstd.application.platform.identity.command.CreateMenuCommand;
import com.mayanshe.scrmstd.application.platform.identity.command.DeleteMenuCommand;
import com.mayanshe.scrmstd.application.platform.identity.command.ModifyMenuCommand;
import com.mayanshe.scrmstd.application.platform.identity.dto.MenuDto;
import com.mayanshe.scrmstd.application.platform.identity.query.MenuDetailQuery;
import com.mayanshe.scrmstd.application.platform.identity.query.MenuOptionQuery;
import com.mayanshe.scrmstd.application.platform.identity.query.MenuPaginationQuery;
import com.mayanshe.scrmstd.bossapi.requests.CreateMenuRequest;
import com.mayanshe.scrmstd.bossapi.requests.ModifyMenuRequest;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import com.mayanshe.scrmstd.shared.contract.RestResponse;
import com.mayanshe.scrmstd.shared.model.Pagination;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MenuController: 菜单管理控制器
 */
@RestController
@RequestMapping("/menus")
public class MenuController {

    @Resource(name = "simpleCommandBus")
    private CommandBus commandBus;

    @Resource(name = "simpleQueryBus")
    private QueryBus queryBus;

    /**
     * 创建菜单
     */
    @PostMapping
    public RestResponse<String> create(@RequestBody @Valid CreateMenuRequest request) {
        CreateMenuCommand command = CreateMenuCommand.builder()
                .parentId(request.getParentId() != null ? IdGenerator.fromBase62(request.getParentId()) : 0L)
                .kind(request.getKind())
                .name(request.getName())
                .title(request.getTitle())
                .path(request.getPath())
                .redirect(request.getRedirect())
                .component(request.getComponent())
                .icon(request.getIcon())
                .sort(request.getSort())
                .isExternal(request.getIsExternal())
                .externalLink(request.getExternalLink())
                .keepAlive(request.getKeepAlive())
                .hideInMenu(request.getHideInMenu())
                .hideChildrenInMenu(request.getHideChildrenInMenu())
                .requiresAuth(request.getRequiresAuth())
                .permission(request.getPermission())
                .status(request.getStatus())
                .remark(request.getRemark())
                .build();

        Long id = commandBus.dispatch(command);
        return RestResponse.success(IdGenerator.toBase62(id));
    }

    /**
     * 修改菜单
     */
    @PutMapping("/{id}")
    public RestResponse<Boolean> modify(@PathVariable String id, @RequestBody @Valid ModifyMenuRequest request) {
        ModifyMenuCommand command = ModifyMenuCommand.builder()
                .id(IdGenerator.fromBase62(id))
                .parentId(request.getParentId() != null ? IdGenerator.fromBase62(request.getParentId()) : 0L)
                .kind(request.getKind())
                .name(request.getName())
                .title(request.getTitle())
                .path(request.getPath())
                .redirect(request.getRedirect())
                .component(request.getComponent())
                .icon(request.getIcon())
                .sort(request.getSort())
                .isExternal(request.getIsExternal())
                .externalLink(request.getExternalLink())
                .keepAlive(request.getKeepAlive())
                .hideInMenu(request.getHideInMenu())
                .hideChildrenInMenu(request.getHideChildrenInMenu())
                .requiresAuth(request.getRequiresAuth())
                .permission(request.getPermission())
                .status(request.getStatus())
                .remark(request.getRemark())
                .build();

        return RestResponse.success(commandBus.dispatch(command));
    }

    /**
     * 删除菜单
     */
    @DeleteMapping("/{id}")
    public RestResponse<Boolean> delete(@PathVariable String id) {
        DeleteMenuCommand command = DeleteMenuCommand.builder()
                .id(IdGenerator.fromBase62(id))
                .build();
        return RestResponse.success(commandBus.dispatch(command));
    }

    /**
     * 获取菜单详情
     */
    @GetMapping("/{id}")
    public RestResponse<MenuDto> detail(@PathVariable String id) {
        MenuDetailQuery query = MenuDetailQuery.builder()
                .id(IdGenerator.fromBase62(id))
                .build();
        return RestResponse.success(queryBus.dispatch(query));
    }

    /**
     * 菜单分页查询
     */
    @GetMapping
    public RestResponse<Pagination<MenuDto>> paginate(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String parentId,
            @RequestParam(required = false) Byte kind,
            @RequestParam(required = false) String keywords,
            @RequestParam(required = false) Byte status
    ) {
        MenuPaginationQuery query = MenuPaginationQuery.builder()
                .page(page)
                .size(size)
                .parentId(parentId != null ? IdGenerator.fromBase62(parentId) : null)
                .kind(kind)
                .keywords(keywords)
                .status(status)
                .build();
        return RestResponse.success(queryBus.dispatch(query));
    }

    /**
     * 菜单列表查询(用于下拉选项等)
     */
    @GetMapping("/options")
    public RestResponse<List<OptionDto>> options(
            @RequestParam(required = false) String parentId,
            @RequestParam(required = false) Byte kind,
            @RequestParam(required = false) String keywords,
            @RequestParam(required = false) Byte status
    ) {
        MenuOptionQuery query = MenuOptionQuery.builder()
                .parentId(parentId != null ? IdGenerator.fromBase62(parentId) : null)
                .kind(kind)
                .keywords(keywords)
                .status(status)
                .build();
        return RestResponse.success(queryBus.dispatch(query));
    }
}
