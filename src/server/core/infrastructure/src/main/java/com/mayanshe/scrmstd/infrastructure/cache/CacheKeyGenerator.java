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
package com.mayanshe.scrmstd.infrastructure.cache;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Cache: 缓存键生成
 */
public final class CacheKeyGenerator {
    public static final String DOMAIN = "SCRMSTD";           // 缓存域名前缀

    private CacheKeyGenerator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static String generate(String key) {
        return String.format("%s::%s", DOMAIN, key.toUpperCase());
    }

    public static String generate(String key, String value) {
        return String.format("%s::%s:%s", DOMAIN, key.toUpperCase(), value);
    }

    public static String generate(String key, short value) {
        return generate(key, String.valueOf(value));
    }

    public static String generate(String key, int value) {
        return generate(key, String.valueOf(value));
    }

    public static String generate(String key, long value) {
        return generate(key, String.valueOf(value));
    }

    public static String generate(String key, double value) {
        return generate(key, String.valueOf(value));
    }

    public static String generate(String key, Object... values) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringBuilder value = new StringBuilder();
            for (Object val : values) {
                value.append(objectMapper.writeValueAsString(val)).append(",");
            }
            return CacheKeyGenerator.generate(key, value.toString());
        } catch (Exception e) {
            throw new RuntimeException("生成缓存键失败", e);
        }
    }

    // 我是分割线 --------------------------------------------------------------------------------------------------------

    /**
     * 生成获取腾讯云临时秘钥缓存键
     *
     * @param isPublicRead 是否公共读
     * @return 缓存键
     */
    public static String getTencentCosCredentialsKey(boolean isPublicRead) {
        return generate("TENCENT-COS-CREDENTIALS", isPublicRead ? "PUBLIC" : "PRIVATE");
    }

}
