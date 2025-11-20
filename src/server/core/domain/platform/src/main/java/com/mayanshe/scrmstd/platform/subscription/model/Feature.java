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
package com.mayanshe.scrmstd.platform.subscription.model;

import com.mayanshe.scrmstd.shared.exception.BadRequestException;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import com.mayanshe.scrmstd.platform.subscription.event.ActivateFeatureEvent;
import com.mayanshe.scrmstd.platform.subscription.event.CreateFeatureEvent;
import com.mayanshe.scrmstd.platform.subscription.event.DestroyFeatureEvent;
import com.mayanshe.scrmstd.platform.subscription.event.ModifyFeatureEvent;
import com.mayanshe.scrmstd.shared.base.Aggregate;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Feature extends Aggregate {
    private AggregateId id;

    private Long parentId;

    private String featureName;

    private String displayName;

    private String description;

    private Boolean configurable;

    private boolean deleted;

    @Builder.Default
    private Long version = 0L;

    /**
     * 创建功能点
     */
    public void create() {
        this.setDeleted(false);

        CreateFeatureEvent event = CreateFeatureEvent.builder()
                .refId(this.getId().id())
                .featureId(this.getId())
                .parentId(this.parentId)
                .featureName(this.featureName)
                .displayName(this.displayName)
                .description(this.description)
                .build();
        this.registerEvent(event);
    }

    /**
     * 修改功能点
     */
    public void modify(Long parentId, String featureName, String displayName, String description) {
        this.setDeleted(false);

        this.setParentId(this.getParentId());
        this.setFeatureName(this.getFeatureName());
        this.setDisplayName(this.getDisplayName());
        this.setDescription(this.getDescription());

        ModifyFeatureEvent event = ModifyFeatureEvent.builder()
                .refId(this.getId().id())
                .featureId(this.getId())
                .parentId(parentId)
                .featureName(featureName)
                .displayName(displayName)
                .description(description)
                .build();
        this.registerEvent(event);
    }

    /**
     * 禁用功能点
     */
    public void destroy() {
        if (this.deleted) {
            throw new BadRequestException("功能点已被禁用，功能点ID：" + this.id);
        }

        this.setDeleted(true);

        DestroyFeatureEvent event = DestroyFeatureEvent.builder()
                .refId(this.getId().id())
                .featureId(this.getId())
                .build();
        this.registerEvent(event);
    }

    /**
     * 启用功能点
     */
    public void activate() {
        if (!this.deleted) {
            throw new BadRequestException("功能点已被启用，功能点ID：" + this.id);
        }

        this.setDeleted(false);

        ActivateFeatureEvent event = ActivateFeatureEvent.builder()
                .refId(this.getId().id())
                .featureId(this.getId())
                .build();
        this.registerEvent(event);
    }
}
