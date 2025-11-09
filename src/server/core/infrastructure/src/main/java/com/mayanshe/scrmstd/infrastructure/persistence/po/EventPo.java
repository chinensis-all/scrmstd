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

import lombok.*;
import org.apache.ibatis.annotations.Delete;

/**
 * EventPo: 事件持久化对象
 */
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class EventPo {
    private Long id;                            // 自增ID

    private Long refId;                         // 关联ID，比如用户ID，订单ID等

    private String eventType;                   // 事件类型

    private String eventId;                     // 事件ID，幂等字段

    private String eventData;                   // 事件数据，json格式

    private Long occurredAt;                    // 事件发生时间

    private Short state;                        // 0-未发布，1-已发布，2-发布失败

    @Builder.Default
    private String createdBy = "system";        // 事件创建者ID

    @Builder.Default
    private Long createdAt = 0L;                // 创建时间

    @Builder.Default
    private Long updatedAt = 0L;                // 更新时间

    @Builder.Default
    private Long version = 0L;                   // 乐观锁
}
