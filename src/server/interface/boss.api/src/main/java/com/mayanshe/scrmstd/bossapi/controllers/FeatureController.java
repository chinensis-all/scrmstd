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

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.mayanshe.scrmstd.application.CommandBus;
import com.mayanshe.scrmstd.application.OptionDto;
import com.mayanshe.scrmstd.application.QueryBus;
import com.mayanshe.scrmstd.application.platform.command.*;
import com.mayanshe.scrmstd.application.platform.query.FeatureDetailQuery;
import com.mayanshe.scrmstd.application.platform.query.FeatureOptionQuery;
import com.mayanshe.scrmstd.application.platform.query.FeaturePaginationQuery;
import com.mayanshe.scrmstd.application.platform.query.dto.FeatureDto;
import com.mayanshe.scrmstd.bossapi.requests.CreateFeatureRequest;
import com.mayanshe.scrmstd.bossapi.requests.ModifyFeaturePermissionsRequest;
import com.mayanshe.scrmstd.bossapi.requests.ModifyFeatureRequest;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import com.mayanshe.scrmstd.shared.model.IDResponse;
import com.mayanshe.scrmstd.shared.model.Pagination;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * FeatureController: 功能点控制器
 */
@RestController
@RequestMapping("/features")
public class FeatureController {
    @Resource(name = "simpleCommandBus")
    private CommandBus commandBus;

    @Resource(name = "simpleQueryBus")
    private QueryBus queryBus;

    /**
     * 创建Saas功能点
     *
     * @param request 创建请求
     * @return 功能点ID
     */
    @PostMapping
    @SaCheckLogin
    public IDResponse createFeature(@Valid @RequestBody CreateFeatureRequest request) {
        String parentId = request.getParentId();

        Long id = commandBus.execute(new CreateFeatureCommand(
                parentId == null || parentId.isBlank() ? 0L : IdGenerator.fromBase62(request.getParentId()),
                request.getFeatureName(),
                request.getDisplayName(),
                request.getDescription()
        ));

        return new IDResponse(IdGenerator.toBase62(id));
    }

    /**
     * 修改功能点
     *
     * @param id      功能点ID
     * @param request 修改请求
     */
    @PutMapping("/{id}")
    @SaCheckLogin
    public void modifyFeature(@PathVariable("id") String id, @Valid @RequestBody ModifyFeatureRequest request) {
        String parentId = request.getParentId();

        commandBus.execute(new ModifyFeatureCommand(
                id == null || id.isBlank() ? 0L : IdGenerator.fromBase62(id),
                parentId == null || parentId.isBlank() ? 0L : IdGenerator.fromBase62(request.getParentId()),
                request.getFeatureName(),
                request.getDisplayName(),
                request.getDescription()
        ));
    }

    /**
     * 禁用功能点
     *
     * @param id 功能点ID
     */
    @PostMapping("/{id}/lock")
    @SaCheckLogin
    public void destroyFeature(@PathVariable("id") String id) {
        commandBus.execute(new DestroyFeatureCommand(IdGenerator.fromBase62(id)));
    }

    /**
     * 激活功能点
     *
     * @param id 功能点ID
     */
    @DeleteMapping("/{id}/lock")
    @SaCheckLogin
    public void activateFeature(@PathVariable("id") String id) {
        commandBus.execute(new ActivateFeatureCommand(IdGenerator.fromBase62(id)));
    }

    /**
     * 获取功能点详情
     *
     * @param id 功能点ID
     * @return 功能点详情
     */
    @GetMapping("/{id}")
    @SaCheckLogin
    public FeatureDto getFeatureDetail(@PathVariable("id") String id) {
        return queryBus.execute(new FeatureDetailQuery(IdGenerator.fromBase62(id)));
    }

    /**
     * 获取功能点Option
     *
     * @param parentId 父功能点ID
     * @param keywords 搜索关联词
     * @return 功能点Option列表
     */
    @GetMapping("/options")
    @SaCheckLogin
    public List<OptionDto> getFeatureOptions(@RequestParam(value = "parentId", required = false) String parentId, @RequestParam(value = "keywords", required = false) String keywords) {
        return queryBus.execute(new FeatureOptionQuery(
                parentId == null || parentId.isBlank() ? 0L : IdGenerator.fromBase62(parentId),
                keywords,
                false
        ));
    }

    /**
     * 获取功能点分页列表
     *
     * @param parentId     父类ID
     * @param keywords     搜索关键词
     * @param configurable 是否可配置
     * @param deleted      是否删除
     * @param page         页码
     * @param pageSize     分页大小
     * @return Saas功能点分页
     */
    @GetMapping
    @SaCheckLogin
    public Pagination<FeatureDto> getFeaturePagination(@RequestParam(value = "parentId", required = false) String parentId, @RequestParam(value = "keywords", required = false) String keywords, @RequestParam(value = "configurable", required = false) Boolean configurable, @RequestParam(value = "deleted", required = false) Boolean deleted, @RequestParam(value = "page", required = false, defaultValue = "1") Long page, @RequestParam(value = "pageSize", required = false, defaultValue = "20") Long pageSize) {
        return queryBus.execute(new FeaturePaginationQuery(
                parentId == null || parentId.isBlank() ? 0L : IdGenerator.fromBase62(parentId),
                keywords,
                configurable,
                deleted,
                page, pageSize
        ));
    }

    /**
     * 修改功能点权限关联
     *
     * @param id      功能点ID
     * @param request 修改请求
     */
    @PutMapping("/{id}/permissions")
    public void modifyFeaturePermissions(@PathVariable("id") String id, @Valid @RequestBody ModifyFeaturePermissionsRequest request) {
        Set<Long> permissionIds = request.getPermissionIds() == null || request.getPermissionIds().isEmpty() ? Set.of() :
                request.getPermissionIds().stream().map(IdGenerator::fromBase62).collect(java.util.stream.Collectors.toSet());
        commandBus.execute(new ModifyFeaturePermissionsCommand(IdGenerator.fromBase62(id), permissionIds));
    }
}
