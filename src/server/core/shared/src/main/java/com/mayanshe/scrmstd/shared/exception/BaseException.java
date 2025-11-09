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
 * BaseException: 基础异常类，所有自定义异常应继承此类
 */
public abstract class BaseException extends RuntimeException {
    private String error;

    public BaseException() {
        this("Network Error", "网络异常, 请重试");
    }

    public BaseException(String error, String message) {
        super(message);
        this.error = error;
    }

    public BaseException(String error, String message, Throwable cause) {
        super(message, cause);
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public int getHttpStatus() {
        return 500;
    }
}
