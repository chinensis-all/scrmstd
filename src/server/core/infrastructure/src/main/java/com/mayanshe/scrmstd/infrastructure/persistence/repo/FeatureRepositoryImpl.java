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
package com.mayanshe.scrmstd.infrastructure.persistence.repo;

import com.mayanshe.scrmstd.shared.annotation.SaveDomainEvents;
import com.mayanshe.scrmstd.tenant.subscription.model.Feature;
import com.mayanshe.scrmstd.tenant.subscription.repo.FeatureRepository;
import com.mayanshe.scrmstd.infrastructure.external.converter.FeatureConverter;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.FeatureMapper;
import com.mayanshe.scrmstd.infrastructure.persistence.po.FeaturePo;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

/**
 * FeatureRepositoryImpl: 功能仓储实现
 */
@Repository
public class FeatureRepositoryImpl implements FeatureRepository {
    private final FeatureMapper mapper;

    public FeatureRepositoryImpl(@Lazy FeatureMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Optional<Feature> load(Long id) {
        FeaturePo po = getPo(id);
        return Optional.ofNullable(po == null ? null : FeatureConverter.INSTANCE.toAggregate(po));
    }

    @Override
    @Transactional
    @SaveDomainEvents
    public void save(Feature aggregate) {
        FeaturePo po = FeatureConverter.INSTANCE.toPo(aggregate);

        // 删除功能点
        if (aggregate.isDeleted()) {
            if (mapper.delete(po) <= 0) {
                throw new RuntimeException("删除功能点失败，功能点ID：" + aggregate.getId());
            }
            return;
        }

        verifyConflict(aggregate);

        // 新增功能点
        if (aggregate.getId().newed()) {
            if (mapper.insert(po) <= 0) {
                throw new RuntimeException("新增功能点失败，功能点名称：" + aggregate.getFeatureName());
            }
            return;
        }

        // 更新功能点
        if (mapper.update(po) <= 0) {
            throw new RuntimeException("更新功能点失败，功能点ID：" + aggregate.getId());
        }
    }

    private void verifyConflict(Feature aggregate) {
        Long featureExistingId = mapper.findIdByCondition(Map.of("featureName", aggregate.getFeatureName()));
        if (featureExistingId != null && !featureExistingId.equals(aggregate.getId().id())) {
            throw new RuntimeException("功能点名称已存在，功能点名称：" + aggregate.getFeatureName());
        }

        Long displayNameExistingId = mapper.findIdByCondition(Map.of("displayName", aggregate.getDisplayName()));
        if (displayNameExistingId != null && !displayNameExistingId.equals(aggregate.getId().id())) {
            throw new RuntimeException("功能点显示名称已存在，功能点显示名称：" + aggregate.getDisplayName());
        }
    }

    private FeaturePo getPo(Long id) {
        if (id == null || id <= 0) {
            return null;
        }

        return mapper.findById(id);
    }
}
