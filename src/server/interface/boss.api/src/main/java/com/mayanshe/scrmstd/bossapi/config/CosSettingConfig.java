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
package com.mayanshe.scrmstd.bossapi.config;

import com.mayanshe.scrmstd.infrastructure.external.adapter.cos.CosSetting;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cos配置类
 */
@Configuration
public class CosSettingConfig {
    @Bean
    public CosSetting createCosSettingBean(@Value("${storage.cos.provider}") String provider,
                                          @Value("${storage.cos.uid}") String uid,
                                          @Value("${storage.cos.secret-id:}") String secretId,
                                          @Value("${storage.cos.secret-key}") String secretKey,
                                          @Value("${storage.cos.region}") String region,
                                          @Value("${storage.cos.bucket}") String bucket,
                                          @Value("${storage.cos.public-bucket}") String publicBucket,
                                          @Value("${storage.cos.duration-seconds}") Integer durationSeconds,
                                          @Value("${storage.cos.cdn-domain}") String cdnDomain) {
        return CosSetting.builder()
                .provider(provider)
                .uid(uid)
                .secretId(secretId)
                .secretKey(secretKey)
                .region(region)
                .bucket(bucket)
                .publicBucket(publicBucket)
                .durationSeconds(durationSeconds)
                .cdnDomain(cdnDomain)
                .build();
    }
}
