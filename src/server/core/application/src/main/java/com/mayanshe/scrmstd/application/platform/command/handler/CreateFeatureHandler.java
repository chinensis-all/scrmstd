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
import com.mayanshe.scrmstd.application.platform.command.CreateFeatureCommand;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import com.mayanshe.scrmstd.platform.subscription.model.Feature;
import com.mayanshe.scrmstd.platform.subscription.repo.FeatureRepository;
import org.springframework.stereotype.Component;

/**
 * CreateFeatureHandler: 创建Saas功能点处理器
 */
@Component
public class CreateFeatureHandler implements CommandHandler<CreateFeatureCommand, Long> {
    private final FeatureRepository repository;

    private final DomainEventPublisher publisher;

    private final IdGenerator idGenerator;

    public CreateFeatureHandler(FeatureRepository repository, DomainEventPublisher publisher, IdGenerator idGenerator) {
        this.repository = repository;
        this.publisher = publisher;
        this.idGenerator = idGenerator;
    }

    @Override
    public Long handle(CreateFeatureCommand command) {
        long id = idGenerator.nextId();

        Feature feature = Feature.builder()
                .id(new AggregateId(id, true))
                .parentId(command.parentId())
                .featureName(command.featureName())
                .displayName(command.displayName())
                .description(command.description())
                .build();
        feature.create();

        repository.save(feature);
        publisher.confirm(feature.getEvents());

        return id;
    }
}
