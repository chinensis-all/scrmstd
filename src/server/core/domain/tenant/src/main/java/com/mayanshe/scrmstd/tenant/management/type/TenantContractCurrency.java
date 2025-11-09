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
package com.mayanshe.scrmstd.tenant.management.type;

/**
 * TenantContractCurrency: 租户合同币种
 */
public enum TenantContractCurrency {
    CNY("人民币"), USD("美元"), EUR("欧元"), GBP("英镑"), JPY("日元"), AUD("澳元"), CAD("加元"), CHF("瑞士法郎"), HKD("港币"), SGD("新加坡元"), OTHER("其它");

    private final String name;

    TenantContractCurrency(String name) {
        this.name = name;
    }

    public static String getNameByCode(String code) {
        for (TenantContractCurrency value : values()) {
            if (value.name().equalsIgnoreCase(code)) {
                return value.getName();
            }
        }
        return null;
    }

    public static TenantContractCurrency of(String code) {
        for (TenantContractCurrency value : values()) {
            if (value.name().equalsIgnoreCase(code)) {
                return value;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
