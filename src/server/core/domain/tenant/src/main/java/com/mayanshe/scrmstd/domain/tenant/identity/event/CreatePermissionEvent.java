package com.mayanshe.scrmstd.domain.tenant.identity.event;

import com.mayanshe.scrmstd.shared.contract.DomainEvent;

public record CreatePermissionEvent(
        Long groupId,
        String permissionName,
        String displayName,
        String description
) implements DomainEvent {
}
