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
package com.mayanshe.scrmstd.tenant.management.model;

import com.mayanshe.scrmstd.shared.base.Aggregate;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import com.mayanshe.scrmstd.tenant.management.type.TenantContractCurrency;
import com.mayanshe.scrmstd.tenant.management.type.TenantContractStatus;
import com.mayanshe.scrmstd.tenant.management.type.TenantContractType;
import com.mayanshe.scrmstd.tenant.management.valueobj.TenantContractAttachment;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * TenantContract: 租户合同
 */
@Getter
@Setter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class TenantContract extends Aggregate {
    private AggregateId id;                                                    // 合同ID

    private Long tenantId;                                                     // 租户ID

    private String contractNumber;                                             // 合同编号

    private String title;                                                      // 合同标题

    private TenantContractType contractType = TenantContractType.STANDARD;     // 合同类型

    private LocalDate startDate;                                               // 合同开始日期

    private LocalDate endDate;                                                 // 合同结束日期

    private BigDecimal totalAmount;                                            // 合同总金额

    private TenantContractCurrency currency = TenantContractCurrency.CNY;      // 合同币种

    private TenantContractStatus status = TenantContractStatus.DRAFT;          // 合同状态

    private LocalDate signDate;                                                // 签署日期

    private LocalDate terminationDate;                                         // 终止日期

    private String terminationReason;                                          // 终止原因

    private String description;                                                // 备注

    private List<TenantContractAttachment> attachments = new ArrayList<>();    // 附件

    private Long createdBy;                                                    // 创建人ID
}
