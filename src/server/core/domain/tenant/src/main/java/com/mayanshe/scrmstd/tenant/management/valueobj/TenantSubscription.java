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
package com.mayanshe.scrmstd.tenant.management.valueobj;

import com.mayanshe.scrmstd.tenant.management.type.SubscriptionPlan;

import java.time.LocalDate;

/**
 * TenantSubscription: 租户订阅信息值对象
 *
 * @param plan      订阅计划
 * @param startDate 订阅开始日期
 * @param endDate   订阅结束日期
 */
public record TenantSubscription(
        SubscriptionPlan plan,
        LocalDate startDate,
        LocalDate endDate
) {
    public boolean isActive(LocalDate now) {
        return now.isAfter(startDate) && (endDate == null || now.isBefore(endDate));
    }
}
