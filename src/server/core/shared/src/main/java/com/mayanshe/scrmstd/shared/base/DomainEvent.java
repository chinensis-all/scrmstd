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
package com.mayanshe.scrmstd.shared.base;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mayanshe.scrmstd.shared.util.ServletUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * DomainEvent: 领域事件基类
 */
@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DomainEvent implements Serializable {
    @JsonIgnore
    private Long refId;                          // 关联ID（如用户ID、订单ID等）

    @JsonIgnore
    private String eventType;                    // 事件类型

    @JsonIgnore
    private String eventId;                      // 事件ID（唯一标识）

    @JsonIgnore
    private Long occurredAt;                     // 事件发生时间戳

    /**
     * 获取事件类型，默认为类名
     *
     * @return 事件类型
     */
    public String getEventType() {
        if (eventType == null || eventType.isEmpty()) {
            eventType = this.getClass().getSimpleName();
        }
        return eventType;
    }

    /**
     * 获取事件ID，若无则生成
     *
     * @return 事件ID
     */
    public String getEventId() {
        if (eventId == null || eventId.isBlank()) {
            String eventString = this.toJsonString();
            String xcsrfToken = ServletUtil.getXCSRFToken();
            String md5 = DigestUtil.md5Hex(this.getEventType() + eventString + xcsrfToken);
            eventId = String.format("domain:%s", md5);
        }

        return eventId;
    }

    /**
     * 获取事件发生时间，若无则为当前时间
     *
     * @return 事件发生时间
     */
    public Long getOccurredAt() {
        if (occurredAt == null || occurredAt <= 0) {
            occurredAt = System.currentTimeMillis();
        }
        return occurredAt;
    }

    public String toJsonString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            return this.toString();
        }
    }
}