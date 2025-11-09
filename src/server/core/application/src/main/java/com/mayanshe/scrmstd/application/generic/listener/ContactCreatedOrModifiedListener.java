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
package com.mayanshe.scrmstd.application.generic.listener;

import com.mayanshe.scrmstd.application.CommandBus;
import com.mayanshe.scrmstd.application.generic.command.CreateContactCommand;
import com.mayanshe.scrmstd.application.tentant.listener.TenantCreatedListener;
import com.mayanshe.scrmstd.domain.boss.admin.event.BossAdminCreatedEvent;
import com.mayanshe.scrmstd.domain.boss.admin.event.BossAdminModifiedEvent;
import com.mayanshe.scrmstd.tenant.identity.event.AdminCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ContactCreatedOrModifiedListener {
    private static final Logger logger = LoggerFactory.getLogger(TenantCreatedListener.class);

    private final CommandBus commandBus;

    public ContactCreatedOrModifiedListener(@Qualifier("simpleCommandBus") CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @Async
    @EventListener
    public void onBossAdminCreated(BossAdminCreatedEvent event) {
        logger.info("Received BossAdminCreatedEvent: " + event.toString());

        try {
            handleContactCreatedOrModified(
                    event.getBossAdminId().id(),
                    "boss",
                    event.getFullName(),
                    event.getEmail(),
                    event.getMobile()
            );
            logger.info("Successfully handled BossAdminCreatedEvent: " + event.toString());
        } catch (Exception e) {
            logger.error("Error handling BossAdminCreatedEvent: " + event, e);
        }
    }

    @Async
    @EventListener
    public void onBossAdminModified(BossAdminModifiedEvent event) {
        logger.info("Received BossAdminModifiedEvent: " + event.toString());

        try {
            handleContactCreatedOrModified(
                    event.getBossAdminId().id(),
                    "boss",
                    event.getFullName(),
                    event.getEmail(),
                    event.getMobile()
            );
            logger.info("Successfully handled BossAdminModifiedEvent: " + event.toString());
        } catch (Exception e) {
            logger.error("Error handling BossAdminModifiedEvent: " + event, e);
        }
    }

    /**
     * 处理管理员创建事件
     *
     * @param event 事件对象
     */
    @Async
    @EventListener
    public void onAdminCreated(AdminCreatedEvent event) {
        logger.info("Received AdminCreatedEvent: " + event.toString());

        try {
            handleContactCreatedOrModified(
                    event.getAdminId().id(),
                    "admin",
                    event.getFullName(),
                    event.getEmail(),
                    event.getPhone()
            );
            logger.info("Successfully handled AdminCreatedEvent: " + event.toString());
        } catch (Exception e) {
            logger.error("Error handling AdminCreatedEvent: " + event, e);
        }
    }

    /**
     * 处理管理员修改事件
     *
     * @param event
     */
    @Async
    @EventListener
    public void onAdminModified(AdminCreatedEvent event) {
        logger.info("Received AdminModifiedEvent: " + event.toString());

        try {
            handleContactCreatedOrModified(
                    event.getAdminId().id(),
                    "admin",
                    event.getFullName(),
                    event.getEmail(),
                    event.getPhone()
            );
            logger.info("Successfully handled AdminModifiedEvent: " + event.toString());
        } catch (Exception e) {
            logger.error("Error handling AdminModifiedEvent: " + event, e);
        }
    }

    /**
     * 处理联系人创建或修改事件
     *
     * @param id  ID
     * @param identity 身份标识
     * @param fullName 姓名
     * @param email 电子邮件
     * @param phone 电话
     */
    private void handleContactCreatedOrModified(Long id, String identity, String fullName, String email, String phone) {
        CreateContactCommand command = new CreateContactCommand(
                id,
                identity,
                fullName,
                email,
                phone
        );
        commandBus.execute(command);
    }
}
