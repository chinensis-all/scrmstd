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
package com.mayanshe.scrmstd.application.platform.command.handler;

import com.mayanshe.scrmstd.application.CommandHandler;
import com.mayanshe.scrmstd.application.DomainEventPublisher;
import com.mayanshe.scrmstd.application.platform.command.ModifyFeatureCommand;
import com.mayanshe.scrmstd.shared.exception.ResourceNotFoundException;
import com.mayanshe.scrmstd.platform.subscription.model.Feature;
import com.mayanshe.scrmstd.platform.subscription.repo.FeatureRepository;
import org.springframework.stereotype.Component;

/**
 * ModifyFeatureHandler: 修改Saas功能点处理器
 */
@Component
public class ModifyFeatureHandler implements CommandHandler<ModifyFeatureCommand, Boolean> {
    private final FeatureRepository repository;

    private final DomainEventPublisher publisher;

    public ModifyFeatureHandler(FeatureRepository repository, DomainEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Override
    public Boolean handle(ModifyFeatureCommand command) {
        Feature feature = repository.load(command.id())
                .orElseThrow(() -> new ResourceNotFoundException("Saas功能点不存在: " + command.id()));

        feature.modify(command.parentId(), command.featureName(), command.displayName(), command.description());

        repository.save(feature);
        publisher.confirm(feature.getEvents());

        return true;
    }
}
