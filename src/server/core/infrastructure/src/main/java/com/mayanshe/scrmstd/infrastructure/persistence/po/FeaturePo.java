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
package com.mayanshe.scrmstd.infrastructure.persistence.po;

import lombok.*;

/**
 * FeaturePo: 功能信息持久化对象
 */
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class FeaturePo {
    private Long id;

    private Long parentId;

    private String featureName;

    private String displayName;

    private String description;

    private Byte configurable;

    @Builder.Default
    private Long createdAt = 0L;

    @Builder.Default
    private Long updatedAt = 0L;

    @Builder.Default
    private Long deletedAt = 0L;

    @Builder.Default
    private Long version = 0L;
}
