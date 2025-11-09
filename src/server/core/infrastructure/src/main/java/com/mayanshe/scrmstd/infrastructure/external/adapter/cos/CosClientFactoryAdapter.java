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
package com.mayanshe.scrmstd.infrastructure.external.adapter.cos;

import com.mayanshe.scrmstd.infrastructure.cache.RedisCache;
import com.mayanshe.scrmstd.shared.contract.CosClient;
import com.mayanshe.scrmstd.shared.contract.CosClientFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * CosFactory: 对象存储工厂类
 */
@Component
public class CosClientFactoryAdapter implements CosClientFactory {
    private final CosSetting cosSetting;

    private final RedisCache redisCache;

    public CosClientFactoryAdapter(@Lazy CosSetting cosSetting, @Lazy RedisCache redisCache) {
        this.cosSetting = cosSetting;
        this.redisCache = redisCache;
    }

    /**
     * 获取对象存储客户端
     * 暂时仅实现腾讯云对象存储，如需接入其他云商，请制定实现并在此添加
     *
     * @return ICosClient
     */
    public CosClient getClient(String provider) {
        ArrayList<String> providers = new ArrayList<String>() {{
            add("tencent");
        }};
        provider = provider == null || providers.contains(provider) ? cosSetting.getProvider().toLowerCase() : provider;
        switch (provider) {
            case "tencent":
            default:
                return new TencentCosClient(cosSetting, redisCache);
        }
    }
}
