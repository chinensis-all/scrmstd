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
package com.mayanshe.scrmstd.infrastructure.external.converter;

import com.mayanshe.scrmstd.infrastructure.persistence.po.TenantPo;
import com.mayanshe.scrmstd.tenant.management.model.Tenant;
import com.mayanshe.scrmstd.tenant.management.type.TenantStatus;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TenantConverter extends BaseConverter {
    TenantConverter INSTANCE = Mappers.getMapper(TenantConverter.class);

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "deletedAt", source = "deleted", qualifiedByName = "deletedToDeletedAt"),
            @Mapping(target = "id", source = "id", qualifiedByName = "aggregateIdToId"),

            @Mapping(target = "provinceId", source = "addressInfo.provinceId"),
            @Mapping(target = "province", source = "addressInfo.province"),
            @Mapping(target = "cityId", source = "addressInfo.cityId"),
            @Mapping(target = "city", source = "addressInfo.city"),
            @Mapping(target = "districtId", source = "addressInfo.districtId"),
            @Mapping(target = "district", source = "addressInfo.district"),
            @Mapping(target = "address", source = "addressInfo.address"),

            @Mapping(target = "contactPerson", source = "contactInfo.person"),
            @Mapping(target = "contactPhone", source = "contactInfo.phone"),
            @Mapping(target = "contactEmail", source = "contactInfo.email"),
            @Mapping(target = "contactWechat", source = "contactInfo.wechat"),

            @Mapping(target = "subscriptionPlan", source = "subscription.plan"),
            @Mapping(target = "subscriptionStart", source = "subscription.startDate"),
            @Mapping(target = "subscriptionEnd", source = "subscription.endDate"),

            @Mapping(target = "status", source = "status", qualifiedByName = "tenantStatusToStatus"),
    })
    TenantPo toPo(Tenant aggregate);

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "deletedAt", source = "deleted", qualifiedByName = "deletedToDeletedAt"),
            @Mapping(target = "id", source = "id", qualifiedByName = "aggregateIdToId"),

            @Mapping(target = "provinceId", source = "addressInfo.provinceId"),
            @Mapping(target = "province", source = "addressInfo.province"),
            @Mapping(target = "cityId", source = "addressInfo.cityId"),
            @Mapping(target = "city", source = "addressInfo.city"),
            @Mapping(target = "districtId", source = "addressInfo.districtId"),
            @Mapping(target = "district", source = "addressInfo.district"),
            @Mapping(target = "address", source = "addressInfo.address"),

            @Mapping(target = "contactPerson", source = "contactInfo.person"),
            @Mapping(target = "contactPhone", source = "contactInfo.phone"),
            @Mapping(target = "contactEmail", source = "contactInfo.email"),
            @Mapping(target = "contactWechat", source = "contactInfo.wechat"),

            @Mapping(target = "subscriptionPlan", source = "subscription.plan"),
            @Mapping(target = "subscriptionStart", source = "subscription.startDate"),
            @Mapping(target = "subscriptionEnd", source = "subscription.endDate"),

            @Mapping(target = "status", source = "status", qualifiedByName = "tenantStatusToStatus"),
    })
    TenantPo updatePo(Tenant aggregate, @MappingTarget TenantPo po) ;

    @InheritInverseConfiguration(name = "toPo")
    @Mappings({
            @Mapping(target = "saves", ignore = true),
            @Mapping(target = "deleted", source = "deletedAt", qualifiedByName = "deletedAtToDeleted"),
            @Mapping(target = "id", source = "id", qualifiedByName = "idToAggregateId"),
            @Mapping(target = "status", source = "status", qualifiedByName = "statusToTenantStatus"),
    })
    Tenant toAggregate(TenantPo po);

    @Named("tenantStatusToStatus")
    default String tenantStatusToStatus(TenantStatus status) {
        return status.name();
    }

    @Named("statusToTenantStatus")
    default TenantStatus statusToTenantStatus(String status) {
        return TenantStatus.of(status);
    }
}
