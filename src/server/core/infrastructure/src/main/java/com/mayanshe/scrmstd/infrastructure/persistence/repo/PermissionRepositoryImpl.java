package com.mayanshe.scrmstd.infrastructure.persistence.repo;

import com.mayanshe.scrmstd.domain.tenant.identity.model.Permission;
import com.mayanshe.scrmstd.domain.tenant.identity.repo.PermissionRepository;
import com.mayanshe.scrmstd.infrastructure.external.converter.PermissionConverter;
import com.mayanshe.scrmstd.infrastructure.persistence.mapper.PermissionMapper;
import com.mayanshe.scrmstd.infrastructure.persistence.po.PermissionPo;
import com.mayanshe.scrmstd.shared.contract.DomainEvent;
import com.mayanshe.scrmstd.shared.contract.DomainRepositoryImpl;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionRepositoryImpl extends DomainRepositoryImpl<Permission, Long, PermissionPo, PermissionMapper> implements PermissionRepository {

    protected PermissionRepositoryImpl(PermissionMapper mapper) {
        super(mapper);
    }

    @Override
    protected PermissionPo domainToPo(Permission permission) {
        return PermissionConverter.INSTANCE.toPo(permission);
    }

    @Override
    protected Permission poToDomain(PermissionPo po) {
        return PermissionConverter.INSTANCE.toAggregate(po);
    }

    @Override
    protected void onEvent(DomainEvent event) {

    }
}
