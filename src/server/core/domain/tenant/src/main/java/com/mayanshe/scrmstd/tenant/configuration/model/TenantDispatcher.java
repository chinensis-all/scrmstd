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
package com.mayanshe.scrmstd.tenant.configuration.model;

import com.mayanshe.scrmstd.shared.base.Aggregate;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import com.mayanshe.scrmstd.tenant.configuration.event.TenantDispatcherCreatedEvent;
import com.mayanshe.scrmstd.tenant.configuration.event.TenantDispatcherDeletedEvent;
import com.mayanshe.scrmstd.tenant.configuration.event.TenantDispatcherModifiedEvent;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TenantDispatcher extends Aggregate {
    private AggregateId id;

    private Long tenantId;

    private String dispatcherType;

    private String dispatcherName;

    private String dispatcherClass;

    private String entityClass;

    private Long version;

    @Builder.Default
    public boolean deleted = false;

    /**
     * 验证DispatcherClass是否存在
     */
    public void validate() {
        try {
            Class.forName(this.dispatcherClass);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("为当前租户配置的处理器不存在: " + this.dispatcherClass);
        }

        try {
            Class.forName(this.entityClass);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("为当前租户配置的载体类不存在: " + this.entityClass);
        }
    }

    /**
     * 创建租户调度器
     */
    public void create() {
        validate();
        this.setDeleted(false);

        TenantDispatcherCreatedEvent event = TenantDispatcherCreatedEvent.builder()
                .tenantDispatcherId(this.getId().id())
                .refId(this.getId().id())
                .tenantId(this.getTenantId())
                .dispatcherName(this.getDispatcherName())
                .dispatcherClass(this.getDispatcherClass())
                .build();
        this.registerEvent(event);
    }

    /**
     * 修改租户调度器
     */
    public void modify() {
        validate();
        this.setDeleted(false);

        TenantDispatcherModifiedEvent event = TenantDispatcherModifiedEvent.builder()
                .tenantDispatcherId(this.getId().id())
                .tenantId(this.getTenantId())
                .dispatcherName(this.getDispatcherName())
                .dispatcherClass(this.getDispatcherClass())
                .build();
        this.registerEvent(event);
    }

    /**
     * 删除租户调度器
     */
    public void delete() {
        this.setDeleted(true);

        TenantDispatcherDeletedEvent event = TenantDispatcherDeletedEvent.builder()
                .tenantDispatcherId(this.getId().id())
                .refId(this.getId().id())
                .tenantId(this.getTenantId())
                .dispatcherName(this.getDispatcherName())
                .dispatcherClass(this.getDispatcherClass())
                .build();
        this.registerEvent(event);
    }
}
