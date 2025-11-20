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
import com.mayanshe.scrmstd.application.platform.command.DestroyFeatureCommand;
import com.mayanshe.scrmstd.shared.exception.ResourceNotFoundException;
import com.mayanshe.scrmstd.platform.subscription.model.Feature;
import com.mayanshe.scrmstd.platform.subscription.repo.FeatureRepository;
import org.springframework.stereotype.Component;

/**
 * DestroyFeatureHandler: 销毁Saas功能点处理器
 */
@Component
public class DestroyFeatureHandler implements CommandHandler<DestroyFeatureCommand, Boolean> {
    private final FeatureRepository repository;

    private final DomainEventPublisher publisher;

    public DestroyFeatureHandler(FeatureRepository repository, DomainEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Override
    public Boolean handle(DestroyFeatureCommand command) {
        Feature feature = repository.load(command.id())
                .orElseThrow(() -> new ResourceNotFoundException("Saas功能点不存在: " + command.id()));
        feature.destroy();

        repository.save(feature);
        publisher.confirm(feature.getEvents());

        return true;
    }
}
