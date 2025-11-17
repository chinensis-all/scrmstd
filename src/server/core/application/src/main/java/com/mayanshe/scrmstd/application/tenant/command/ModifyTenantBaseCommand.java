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
package com.mayanshe.scrmstd.application.tentant.command;

import com.mayanshe.scrmstd.application.Command;
import com.mayanshe.scrmstd.shared.model.IDResponse;

/**
 * ModifyTenantBaseCommand: 修改租户基础信息命令载体
 *
 * @param tenantName   租户名称
 * @param legalName    法人名称
 * @param creditCode   统一社会信用代码
 * @param industryCode 行业编码
 * @param industryName 行业名称
 */
public record ModifyTenantBaseCommand(
        Long id,
        String tenantName,
        String legalName,
        String creditCode,
        String industryCode,
        String industryName
) implements Command<Boolean> {}
