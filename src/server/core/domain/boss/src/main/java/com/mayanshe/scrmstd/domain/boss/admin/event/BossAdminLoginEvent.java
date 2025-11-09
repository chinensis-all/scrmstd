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
package com.mayanshe.scrmstd.domain.boss.admin.event;

import com.mayanshe.scrmstd.shared.base.DomainEvent;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 事件 : 管理员登录
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString(callSuper = false)
public class BossAdminLoginEvent extends DomainEvent {
    private AggregateId bossAdminId;    // 管理员ID

    private Long loginAt;               // 最后登录时间

    private String loginIp;             // 最后登录IP
}
