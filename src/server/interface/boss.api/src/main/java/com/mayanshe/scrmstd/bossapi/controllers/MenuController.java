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
import com.mayanshe.scrmstd.application.platform.command.CreateMenuCommand;
import com.mayanshe.scrmstd.application.platform.command.DeleteMenuCommand;
import com.mayanshe.scrmstd.application.platform.command.ModifyMenuCommand;
import com.mayanshe.scrmstd.application.platform.query.dto.MenuDto;
import com.mayanshe.scrmstd.application.platform.query.MenuDetailQuery;
import com.mayanshe.scrmstd.application.platform.query.MenuOptionQuery;
import com.mayanshe.scrmstd.application.platform.query.MenuPaginationQuery;
import com.mayanshe.scrmstd.bossapi.requests.CreateMenuRequest;
import com.mayanshe.scrmstd.bossapi.requests.ModifyMenuRequest;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import com.mayanshe.scrmstd.shared.model.IDResponse;
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
    public IDResponse createMenu(@RequestBody @Valid CreateMenuRequest request) {
        CreateMenuCommand command = new CreateMenuCommand(
                request.getParentId() != null ? IdGenerator.fromBase62(request.getParentId()) : 0L,
                request.getKind(),
                request.getName(),
                request.getTitle(),
                request.getPath(),
                request.getRedirect(),
                request.getComponent(),
                request.getIcon(),
                request.getSort(),
                request.getIsExternal(),
                request.getExternalLink(),
                request.getKeepAlive(),
                request.getHideInMenu(),
                request.getHideChildrenInMenu(),
                request.getRequiresAuth(),
                request.getPermission(),
                request.getStatus(),
                request.getRemark()
        );

        Long id = commandBus.execute(command);

        return new IDResponse(IdGenerator.toBase62(id));
    }

    /**
     * 修改菜单
     */
    @PutMapping("/{id}")
    public void modifyMeny(@PathVariable String id, @RequestBody @Valid ModifyMenuRequest request) {
        ModifyMenuCommand command = new ModifyMenuCommand(
                IdGenerator.fromBase62(id),
                request.getParentId() != null ? IdGenerator.fromBase62(request.getParentId()) : 0L,
                request.getKind(),
                request.getName(),
                request.getTitle(),
                request.getPath(),
                request.getRedirect(),
                request.getComponent(),
                request.getIcon(),
                request.getSort(),
                request.getIsExternal(),
                request.getExternalLink(),
                request.getKeepAlive(),
                request.getHideInMenu(),
                request.getHideChildrenInMenu(),
                request.getRequiresAuth(),
                request.getPermission(),
                request.getStatus(),
                request.getRemark()
        );

        commandBus.execute(command);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        DeleteMenuCommand command = new DeleteMenuCommand(
                IdGenerator.fromBase62(id)
        );

        commandBus.execute(command);
    }

    /**
     * 菜单详情
     *
     * @param id 菜单ID
     * @return   菜单详情
     */
    @GetMapping("/{id}")
    public MenuDto detail(@PathVariable String id) {
        MenuDetailQuery query = new MenuDetailQuery(
                IdGenerator.fromBase62(id)
        );

        return queryBus.execute(query);
    }

    /**
     * 菜单分页查询
     *
     * @param page     页码
     * @param pageSize 分页大小
     * @param parentId 父级ID
     * @param kind     菜单类型
     * @param keywords 关键词
     * @param status   状态
     * @return         菜单分页结果
     */
    @GetMapping
    public Pagination<MenuDto> paginate(
            @RequestParam(value = "page", defaultValue = "1") long page,
            @RequestParam(value = "pageSize", defaultValue = "20") long pageSize,
            @RequestParam(value = "parentId", required = false) String parentId,
            @RequestParam(value = "kind",required = false) Byte kind,
            @RequestParam(value = "keywords", required = false) String keywords,
            @RequestParam(value = "status", required = false) Byte status
    ) {
        MenuPaginationQuery query = new MenuPaginationQuery(
                parentId != null ? IdGenerator.fromBase62(parentId) : null,
                kind,
                keywords,
                status,
                page,
                pageSize
        );

        return queryBus.execute(query);
    }

    /**
     * 菜单列表查询(用于下拉选项等)
     */
    @GetMapping("/options")
    public List<OptionDto> options(
            @RequestParam(required = false) String parentId,
            @RequestParam(required = false) Byte kind,
            @RequestParam(required = false) String keywords,
            @RequestParam(required = false) Byte status
    ) {
        MenuOptionQuery query = new MenuOptionQuery(
                parentId != null ? IdGenerator.fromBase62(parentId) : null,
                kind,
                keywords,
                status
        );

        return queryBus.execute(query);
    }
}
