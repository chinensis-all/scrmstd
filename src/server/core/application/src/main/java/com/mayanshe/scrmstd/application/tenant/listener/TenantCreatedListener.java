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
package com.mayanshe.scrmstd.application.tentant.listener;

import cn.hutool.core.util.RandomUtil;
import com.mayanshe.scrmstd.application.CommandBus;
import com.mayanshe.scrmstd.application.tentant.command.CreateAdminCommand;
import com.mayanshe.scrmstd.application.tentant.command.CreateRoleCommand;
import com.mayanshe.scrmstd.shared.model.IDResponse;
import com.mayanshe.scrmstd.tenant.management.event.TenantCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TenantCreatedListener {
    private static final Logger logger = LoggerFactory.getLogger(TenantCreatedListener.class);

    private final CommandBus commandBus;

    public TenantCreatedListener(@Qualifier("simpleCommandBus") CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @Async
    @EventListener
    public void onTenantCreated(TenantCreatedEvent event) {
        logger.info("Received TenantCreatedEvent for tenantId: " + event.getTenantId());

        try {
            initializeTenantResources(event);
            logger.info("Successfully handled TenantCreatedEvent for tenantId: " + event.getTenantId());
        } catch (Exception e) {
            logger.error("Error handling TenantCreatedEvent for tenantId: " + event.getTenantId(), e);
        }
    }

    /**
     * 初始化租户资源
     *
     * @param event 事件对象
     */
    private void initializeTenantResources(TenantCreatedEvent event) {
        Long roleId = createRole(event.getTenantId().id());
        createAdministrator(event, roleId);
    }

    /**
     * 创建超管账号
     *
     * @param event  事件对象
     * @param roleId 角色ID
     */
    private void createAdministrator(TenantCreatedEvent event, Long roleId) {
        CreateAdminCommand command = new CreateAdminCommand
                (event.getTenantId().id(),
                        "admin_" + event.getContactInfo().phone(),
                        RandomUtil.randomString(16),
                        event.getContactInfo().person(),
                        event.getContactInfo().email(),
                        event.getContactInfo().phone(),
                        "",
                        new ArrayList<>() {{
                            add(roleId);
                        }},
                        true
                );
        commandBus.execute(command);
    }

    /**
     * 创建超级管理员角色
     *
     * @param tenantId 租户ID
     * @return 角色ID
     */
    private Long createRole(Long tenantId) {
        CreateRoleCommand command = new CreateRoleCommand(tenantId, "超级管理员", "拥有所有权限；不可编辑；不可删除。");
        IDResponse response = commandBus.execute(command);
        return Long.parseLong(response.id());
    }
}
