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
package com.mayanshe.scrmstd.tenant.management.event;

import com.mayanshe.scrmstd.shared.base.DomainEvent;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import com.mayanshe.scrmstd.tenant.management.valueobj.TenantAddress;
import com.mayanshe.scrmstd.tenant.management.valueobj.TenantContactInfo;
import com.mayanshe.scrmstd.tenant.management.valueobj.TenantSubscription;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString(callSuper = false)
public class TenantCreatedEvent extends DomainEvent {
    private AggregateId tenantId;                                       // 租户ID

    private String tenantName;                                         // 租户名称

    private String legalName;                                          // 法人代表

    private String creditCode;                                         // 统一社会信用代码

    private String industryCode;                                       // 行业编码

    private TenantAddress addressInfo;                                 // 地址信息

    private TenantContactInfo contactInfo;                             // 联系人信息
}
