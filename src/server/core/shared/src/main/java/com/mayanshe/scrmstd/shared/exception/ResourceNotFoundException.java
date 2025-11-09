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
 * ResourceNotFoundException: 资源未找到异常
 */
public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException() {
        super("Resource Not Found", "请求的资源未找到");
    }

    public ResourceNotFoundException(String message) {
        super("Resource Not Found", message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super("Resource Not Found", message, cause);
    }

    @Override
    public int getHttpStatus() {
        return 404;
    }
}
