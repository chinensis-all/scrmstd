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

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TenantPo {
    private Long id;                           // ID 租户编码

    private String tenantName;                 // 租户名称（企业或组织名称）

    private String legalName;                  // 法人姓名

    private String creditCode;                 // 统一社会信用代码

    private String industryCode;               // 行业分类代码（基于 GB/T 4754-2017，例如 A01 为农业）

    private String industryName;               // 行业分类名称(冗余)

    private Long provinceId;                   // 所在省ID

    private String province;                   // 所在省名称

    private Long cityId;                       // 所在市ID

    private String city;                       // 所在市名称

    private Long districtId;                   // 所在区县ID

    private String district;                   // 所在区县名称

    private String address;                    // 详细地址

    private String contactPerson;              // 联系人姓名

    private String contactPhone;               // 联系电话（支持中国手机号格式）

    private String contactEmail;               // 联系邮箱

    private String contactWechat;              // 联系微信号（中国市场常用）

    private String subscriptionPlan;           // 订阅计划（基础、标准、高级、企业级）

    private String subscriptionStart;          // 订阅开始日期

    private String subscriptionEnd;            // 订阅结束日期（NULL 表示无限期）

    private String status;                     // 租户状态（活跃、非活跃、暂停、试用）

    @Builder.Default
    private long createdAt = 0L;               // 创建时间

    @Builder.Default
    private long updatedAt = 0L;               // 更新时间

    @Builder.Default
    private long deletedAt = 0L;               // 软删除时间

    public String getSubscriptionPlan() {
        return this.subscriptionPlan.toUpperCase();
    }
}
