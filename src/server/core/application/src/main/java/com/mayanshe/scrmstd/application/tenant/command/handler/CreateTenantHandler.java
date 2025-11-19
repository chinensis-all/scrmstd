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
package com.mayanshe.scrmstd.application.tenant.command.handler;

import com.mayanshe.scrmstd.application.CommandHandler;
import com.mayanshe.scrmstd.application.DomainEventPublisher;
import com.mayanshe.scrmstd.application.tenant.command.CreateTenantCommand;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import com.mayanshe.scrmstd.shared.model.AggregateId;
import com.mayanshe.scrmstd.tenant.management.model.Tenant;
import com.mayanshe.scrmstd.tenant.management.repo.TenantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateTenantHandler implements CommandHandler<CreateTenantCommand, Long> {
    private final IdGenerator idGenerator;

    private final DomainEventPublisher publisher;

    private final TenantRepository tenantRepository;

    public CreateTenantHandler(IdGenerator idGenerator, DomainEventPublisher publisher, TenantRepository tenantRepository) {
        this.idGenerator = idGenerator;
        this.publisher = publisher;
        this.tenantRepository = tenantRepository;
    }

    @Override
    @Transactional
    public Long handle(CreateTenantCommand command) {
        long id = idGenerator.nextId();

        Tenant aggregate = Tenant.builder()
                .id(new AggregateId(id, true))
                .tenantName(command.tenantName())
                .legalName(command.legalName())
                .creditCode(command.creditCode())
                .industryCode(command.industryCode())
                .industryName(command.industryName())
                .build();
        aggregate.addAddressInfo(command.provinceId(), command.province(), command.cityId(), command.city(), command.districtId(), command.district(), command.address());
        aggregate.addContactInfo(command.contactPerson(), command.contactPhone(), command.contactEmail(), command.contactWechat());
        aggregate.create();

        tenantRepository.save(aggregate);
        publisher.confirm(aggregate.getEvents());

        return id;
    }
}
