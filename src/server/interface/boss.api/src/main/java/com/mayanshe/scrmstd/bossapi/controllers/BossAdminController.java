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
import com.mayanshe.scrmstd.application.boss.command.*;
import com.mayanshe.scrmstd.application.boss.query.BossAdminDetailQuery;
import com.mayanshe.scrmstd.application.boss.query.BossAdminOptionQuery;
import com.mayanshe.scrmstd.application.boss.query.BossAdminPaginationQuery;
import com.mayanshe.scrmstd.application.boss.query.dto.BossAdminDto;
import com.mayanshe.scrmstd.bossapi.requests.CreateBossAdminRequest;
import com.mayanshe.scrmstd.bossapi.requests.LoginBossAdminRequest;
import com.mayanshe.scrmstd.bossapi.requests.ModifyBossAdminRequest;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import com.mayanshe.scrmstd.shared.model.AccessToken;
import com.mayanshe.scrmstd.shared.model.IDResponse;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boss-admins")
public class BossAdminController {
    @Resource(name = "simpleCommandBus")
    private CommandBus commandBus;

    @Resource(name = "simpleQueryBus")
    private QueryBus queryBus;

    @PostMapping
    public IDResponse createAdmin(@Valid @RequestBody CreateBossAdminRequest request) {
        CreateBossAdminCommand command = new CreateBossAdminCommand(request.getUsername(), request.getPassword(), request.getFullName(), request.getPhone(), request.getEmail(), request.getAvatar());
        Long id = commandBus.execute(command);
        return new IDResponse(IdGenerator.toBase62(id));
    }

    @PutMapping("/{id}")
    public void modifyAdmin(@PathVariable("id") String id, @Valid @RequestBody ModifyBossAdminRequest request) {
        ModifyBossAdminCommand command = new ModifyBossAdminCommand(IdGenerator.fromBase62(id), request.getUsername(), request.getPassword(), request.getFullName(), request.getPhone(), request.getEmail(), request.getAvatar());
        commandBus.execute(command);
    }

    @PostMapping("/{id}/lock")
    public void destroyAdmin(@PathVariable("id") String id) {
        DestroyedBossAdminCommand command = new DestroyedBossAdminCommand(IdGenerator.fromBase62(id));
        commandBus.execute(command);
    }

    @DeleteMapping("/{id}/lock")
    public void activateAdmin(@PathVariable("id") String id) {
        ActivateBossAdminCommand command = new ActivateBossAdminCommand(IdGenerator.fromBase62(id));
        commandBus.execute(command);
    }

    @GetMapping("/{id}")
    public BossAdminDto getAdminDetail(@PathVariable("id") String id) {
        return queryBus.execute(new BossAdminDetailQuery(IdGenerator.fromBase62(id)));
    }

    @GetMapping("/options")
    public Object getAdminOptions(@RequestParam(value = "keywords", required = false) String keywords) {
        return queryBus.execute(new BossAdminOptionQuery(keywords, 200));
    }

    @GetMapping
    public Object getAdminPagination(@RequestParam(value = "keywords", required = false) String keywords,
                                     @RequestParam(value = "deleted", required = false) Boolean deleted,
                                     @RequestParam(value = "page", required = false) Long page,
                                     @RequestParam(value = "pageSize", required = false) Long pageSize) {
        return queryBus.execute(new BossAdminPaginationQuery(keywords, deleted, page, pageSize));
    }

    @PostMapping("/access-token")
    public AccessToken createAccessToken(@Valid @RequestBody LoginBossAdminRequest request) {
        return commandBus.execute(new LoginBossAdminCommand(request.getAccount(), request.getPassword(), request.getRememberMe()));
    }
}
