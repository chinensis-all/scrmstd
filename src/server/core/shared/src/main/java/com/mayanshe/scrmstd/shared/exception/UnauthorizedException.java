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
package com.mayanshe.scrmstd.shared.exception;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException() {
        super("Unauthorized", "您未登录或登录已过期");
    }

    public UnauthorizedException(String message) {
        super("Unauthorized", message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super("Unauthorized", message, cause);
    }

    @Override
    public int getHttpStatus() {
        return 401;
    }
}
