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

import lombok.*;

/**
 * 对象存储配置类
 */
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CosSetting {

    public String provider;                      // 提供者

    public String uid;                           // 用户ID

    public String secretId;                      // SecretId

    public String secretKey;                     // SecretKey

    public String region;                        // 所在区域

    public String bucket;                        // 存储桶

    public String publicBucket;                  // 公开读存储桶(大部分内容都不应该存储在这个桶内)

    public Integer durationSeconds;              // 临时秘钥生存时间

    public String cdnDomain;                     // cdn域名(如果有cdn，请填写此项)

    public void verify() {
        if (this.getProvider() == null || this.getProvider().isBlank() ||
                this.uid == null || this.uid.isBlank() ||
                this.secretId == null || this.secretId.isBlank() ||
                this.secretKey == null || this.secretKey.isBlank() ||
                this.region == null || this.region.isBlank() ||
                this.bucket == null || this.bucket.isBlank() ||
                this.publicBucket == null || this.publicBucket.isBlank() ||
                this.durationSeconds == null || this.durationSeconds < 60
        ) {
            throw new IllegalArgumentException("Cos has not been configured yet!");
        }
    }
}
