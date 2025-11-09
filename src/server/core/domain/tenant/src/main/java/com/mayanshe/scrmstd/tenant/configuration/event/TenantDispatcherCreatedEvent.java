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
package com.mayanshe.scrmstd.tenant.configuration.event;

import com.mayanshe.scrmstd.shared.base.DomainEvent;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * TenantDispatcherCreatedEvent: 租户调度器创建事件
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TenantDispatcherCreatedEvent extends DomainEvent {
    private Long tenantDispatcherId;       // 主键ID

    public Long tenantId;                  // 租户ID

    public String dispatcherName;          // 调度器名称

    public String dispatcherClass;         // 调度器类全名
}
