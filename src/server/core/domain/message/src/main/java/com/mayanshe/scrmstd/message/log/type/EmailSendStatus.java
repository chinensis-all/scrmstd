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
package com.mayanshe.scrmstd.message.log.type;

/**
 * 邮件发送状态枚举
 */
public enum EmailSendStatus {
    /**
     * 已发送
     */
    SENT("sent", "已发送"),

    /**
     * 发送失败
     */
    FAILED("failed", "发送失败"),

    /**
     * 退回
     */
    BOUNCED("bounced", "退回"),

    /**
     * 延迟
     */
    DEFERRED("deferred", "延迟");

    private final String code;
    private final String name;

    EmailSendStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static EmailSendStatus fromCode(String code) {
        for (EmailSendStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return FAILED;
    }
}
