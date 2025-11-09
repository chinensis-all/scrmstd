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

/**
 * TenantAddress: 租户地址值对象
 *
 * @param provinceId 省份ID
 * @param province   省份名称
 * @param cityId     城市ID
 * @param city       城市名称
 * @param districtId 区县ID
 * @param district   区县名称
 * @param address    详细地址
 */
public record TenantAddress(
        Long provinceId,
        String province,
        Long cityId,
        String city,
        Long districtId,
        String district,
        String address
) {}
