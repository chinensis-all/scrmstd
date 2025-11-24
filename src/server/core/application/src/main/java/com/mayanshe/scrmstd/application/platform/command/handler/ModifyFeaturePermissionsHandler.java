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
import com.mayanshe.scrmstd.application.platform.command.ModifyFeaturePermissionsCommand;
import com.mayanshe.scrmstd.platform.subscription.repo.FeatureRepository;
import org.springframework.stereotype.Service;

/**
 * ModifyFeaturePermissionsHandler: 修改功能点权限处理器
 */
@Service
public class ModifyFeaturePermissionsHandler implements CommandHandler<ModifyFeaturePermissionsCommand, Boolean> {
    private final FeatureRepository repository;

    private final DomainEventPublisher publisher;

    public ModifyFeaturePermissionsHandler(FeatureRepository repository, DomainEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Override
    public Boolean handle(ModifyFeaturePermissionsCommand command) {
        var feature = repository.load(command.featureId()).orElseThrow(() -> new RuntimeException("Saas功能点不存在: " + command.featureId()));

        feature.modifyPermissions(command.permissionIds());

        repository.handleModifyFeaturePermissions(feature);
        publisher.confirm(feature.getEvents());

        return true;
    }
}
