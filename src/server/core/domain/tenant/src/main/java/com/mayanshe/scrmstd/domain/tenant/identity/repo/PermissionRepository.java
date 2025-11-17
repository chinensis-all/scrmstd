package com.mayanshe.scrmstd.domain.tenant.identity.repo;

import com.mayanshe.scrmstd.domain.tenant.identity.model.Permission;
import com.mayanshe.scrmstd.shared.contract.DomainRepository;

import java.util.Optional;

public interface PermissionRepository extends DomainRepository<Permission, Long> {
    Optional<Permission> findById(Long id);
}
