package com.mayanshe.scrmstd.infrastructure.external.converter;

import com.mayanshe.scrmstd.application.tenant.query.dto.PermissionDto;
import com.mayanshe.scrmstd.domain.tenant.identity.model.Permission;
import com.mayanshe.scrmstd.infrastructure.persistence.po.PermissionPo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PermissionConverter extends IConverter {
    PermissionConverter INSTANCE = Mappers.getMapper(PermissionConverter.class);

    PermissionPo toPo(Permission permission);

    void updatePo(@MappingTarget PermissionPo po, Permission permission);

    Permission toAggregate(PermissionPo po);

    PermissionDto toDto(PermissionPo po);
}
