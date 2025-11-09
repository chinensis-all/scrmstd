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
import com.mayanshe.scrmstd.bossapi.requests.SendEmailRequest;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 邮件发送控制器
 */
@RestController
@RequestMapping("/emails")
public class EmailController {
    @Resource(name = "simpleCommandBus")
    private CommandBus commandBus;

    /**
     * 手动发送邮件
     */
    @PostMapping("/send")
    public void sendEmail(@Valid @RequestBody SendEmailRequest request) {
        /*SendEmailCommand command = new SendEmailCommand(
                request.getTenantId(),
                IdGenerator.fromBase62(request.getTemplateId()),
                request.getRecipient(),
                request.getCc(),
                request.getParams(),
                request.getAttachments()
        );
        return commandBus.execute(command);*/
    }
}
