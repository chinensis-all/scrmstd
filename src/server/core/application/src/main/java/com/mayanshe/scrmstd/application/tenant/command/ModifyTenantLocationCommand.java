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
package com.mayanshe.scrmstd.application.tenant.command;

import com.mayanshe.scrmstd.application.Command;
import com.mayanshe.scrmstd.shared.model.IDResponse;

/**
 * ModifyTenantLocationCommand: 修改租户地址命令载体
 *
 * @param id         租户ID
 * @param provinceId 省/直辖市ID
 * @param province   省/直辖市
 * @param cityId     市/直辖区
 * @param city       市/直辖区
 * @param districtId 区/县ID
 * @param district   区/县
 * @param address    详细地址
 */
public record ModifyTenantLocationCommand(
        Long id,
        Long provinceId,
        String province,
        Long cityId,
        String city,
        Long districtId,
        String district,
        String address
) implements Command<IDResponse> {}
