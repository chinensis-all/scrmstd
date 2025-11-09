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
package com.mayanshe.scrmstd.saasapi.controllers;

import com.mayanshe.scrmstd.application.CommandBus;
import com.mayanshe.scrmstd.application.QueryBus;
import com.mayanshe.scrmstd.application.message.command.CreateEmailTemplateCommand;
import com.mayanshe.scrmstd.application.message.command.DeleteEmailTemplateCommand;
import com.mayanshe.scrmstd.application.message.command.ModifyEmailTemplateCommand;
import com.mayanshe.scrmstd.application.message.query.EmailTemplateDetailQuery;
import com.mayanshe.scrmstd.application.message.query.EmailTemplateOptionQuery;
import com.mayanshe.scrmstd.application.message.query.EmailTemplatePaginationQuery;
import com.mayanshe.scrmstd.application.message.query.dto.EmailTemplateDto;
import com.mayanshe.scrmstd.saasapi.requests.CreateEmailTemplateRequest;
import com.mayanshe.scrmstd.saasapi.requests.ModifyEmailTemplateRequest;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import com.mayanshe.scrmstd.shared.model.IDResponse;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 邮件模板控制器
 */
@RestController
@RequestMapping("/email-templates")
public class EmailTemplateController {
    @Resource(name = "tenantAwareCommandBus")
    private CommandBus commandBus;

    @Resource(name = "tenantAwareQueryBus")
    private QueryBus queryBus;

    /**
     * 创建邮件模板
     */
    @PostMapping
    public IDResponse createTemplate(@Valid @RequestBody CreateEmailTemplateRequest request) {
        CreateEmailTemplateCommand command = new CreateEmailTemplateCommand(
                request.getTenantId(),
                request.getTemplateName(),
                request.getCategory(),
                request.getSubject(),
                request.getBody(),
                request.getPlaceholders()
        );
        Long id = commandBus.execute(command);
        return new IDResponse(IdGenerator.toBase62(id));
    }

    /**
     * 修改邮件模板
     */
    @PutMapping("/{id}")
    public void modifyTemplate(@PathVariable("id") String id, @Valid @RequestBody ModifyEmailTemplateRequest request) {
        ModifyEmailTemplateCommand command = new ModifyEmailTemplateCommand(
                IdGenerator.fromBase62(id),
                request.getTemplateName(),
                request.getCategory(),
                request.getSubject(),
                request.getBody(),
                request.getPlaceholders()
        );
        commandBus.execute(command);
    }

    /**
     * 删除邮件模板
     */
    @DeleteMapping("/{id}")
    public void deleteTemplate(@PathVariable("id") String id) {
        DeleteEmailTemplateCommand command = new DeleteEmailTemplateCommand(IdGenerator.fromBase62(id));
        commandBus.execute(command);
    }

    /**
     * 获取邮件模板详情
     */
    @GetMapping("/{id}")
    public EmailTemplateDto getTemplateDetail(@PathVariable("id") String id) {
        return queryBus.execute(new EmailTemplateDetailQuery(IdGenerator.fromBase62(id)));
    }

    /**
     * 获取邮件模板选项列表
     */
    @GetMapping("/options")
    public Object getTemplateOptions(@RequestParam(value = "keywords", required = false) String keywords) {
        return queryBus.execute(new EmailTemplateOptionQuery(keywords, 200));
    }

    /**
     * 分页查询邮件模板列表
     */
    @GetMapping
    public Object getTemplatePagination(@RequestParam(value = "keywords", required = false) String keywords,
                                        @RequestParam(value = "category", required = false) String category,
                                        @RequestParam(value = "page", required = false) Long page,
                                        @RequestParam(value = "pageSize", required = false) Long pageSize) {
        return queryBus.execute(new EmailTemplatePaginationQuery(keywords, category, page, pageSize));
    }
}
