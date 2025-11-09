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
 * RequestForbiddenException: 请求被禁止异常，通常用于权限不足的情况
 */
public class RequestForbiddenException extends BaseException {
    public RequestForbiddenException() {
        super("Request Forbidden", "请求被禁止, 请检查权限");
    }

    public RequestForbiddenException(String message) {
        super("Request Forbidden", message);
    }

    public RequestForbiddenException(String message, Throwable cause) {
        super("Request Forbidden", message, cause);
    }

    @Override
    public int getHttpStatus() {
        return 403;
    }
}
