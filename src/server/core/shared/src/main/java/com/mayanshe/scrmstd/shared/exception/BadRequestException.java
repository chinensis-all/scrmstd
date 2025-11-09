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

/**
 * BadRequestException: 用于表示请求错误的异常，一般对应HTTP 400错误
 */
public class BadRequestException extends BaseException {
    public BadRequestException() {
        super("Bad Request", "请求错误, 请重试");
    }

    public BadRequestException(String message) {
        super("Bad Request", message);
    }

    public BadRequestException(String message, Throwable cause) {
        super("Bad Request", message, cause);
    }

    @Override
    public int getHttpStatus() {
        return 400;
    }
}
