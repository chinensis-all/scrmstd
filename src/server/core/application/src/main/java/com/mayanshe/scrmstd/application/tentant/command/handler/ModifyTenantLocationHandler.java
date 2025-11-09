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
package com.mayanshe.scrmstd.application.tentant.command.handler;

import com.mayanshe.scrmstd.application.CommandHandler;
import com.mayanshe.scrmstd.application.DomainEventPublisher;
import com.mayanshe.scrmstd.application.tentant.command.ModifyTenantLocationCommand;
import com.mayanshe.scrmstd.shared.model.IDResponse;
import com.mayanshe.scrmstd.tenant.management.repo.TenantRepository;
import com.mayanshe.scrmstd.tenant.management.valueobj.TenantAddress;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModifyTenantLocationHandler implements CommandHandler<ModifyTenantLocationCommand, IDResponse> {
    private final DomainEventPublisher publisher;

    private final TenantRepository repository;

    public ModifyTenantLocationHandler(DomainEventPublisher publisher, TenantRepository tenantRepository) {
        this.publisher = publisher;
        this.repository = tenantRepository;
    }

    @Transactional
    @Override
    public IDResponse handle(ModifyTenantLocationCommand command) {
        var aggregate = repository.load(command.id()).orElseThrow();

        TenantAddress address = new TenantAddress(command.provinceId(), command.province(), command.cityId(), command.city(), command.districtId(), command.district(), command.address());
        aggregate.modifyLocation(address);

        repository.save(aggregate);
        publisher.confirm(aggregate.getEvents());

        return new IDResponse(String.valueOf(aggregate.getId().id()));
    }
}
