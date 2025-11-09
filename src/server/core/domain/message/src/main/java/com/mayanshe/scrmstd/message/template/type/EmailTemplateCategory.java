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
package com.mayanshe.scrmstd.message.template.type;

/**
 * 邮件模板类别枚举
 */
public enum EmailTemplateCategory {
    /**
     * 通知类邮件
     */
    NOTIFICATION("notification", "通知"),

    /**
     * 营销类邮件
     */
    PROMOTION("promotion", "营销"),

    /**
     * 事务类邮件
     */
    TRANSACTIONAL("transactional", "事务"),

    /**
     * 其他类邮件
     */
    OTHER("other", "其他");

    private final String code;
    private final String name;

    EmailTemplateCategory(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static EmailTemplateCategory fromCode(String code) {
        for (EmailTemplateCategory category : values()) {
            if (category.code.equals(code)) {
                return category;
            }
        }
        return OTHER;
    }
}
