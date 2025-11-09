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
package com.mayanshe.scrmstd.infrastructure.persistence.po;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * TenantApplicationdispatcherPo: 租户应用处理器持久化对象
 */
@Getter
@Setter
@Builder(toBuilder = true)
public class TenantDispatcherPo {
    private Long id;                        // 主键ID

    private Long tenantId;                  // 租户ID

    private String dispatcherType;          // 枚举：dispatcher_TYPE

    private String dispatcherName;          // 处理器名称

    private String dispatcherClass;         // 处理器类全名

    private String entityClass;             // 处理器对应的实体类全名

    @Builder.Default
    private Long createdAt = 0L;            // 创建时间，毫秒时间戳

    @Builder.Default
    private Long updatedAt = 0L;            // 更新时间，毫秒时间戳

    @Builder.Default
    private Long version = 0L;              // 乐观锁版本号
}
