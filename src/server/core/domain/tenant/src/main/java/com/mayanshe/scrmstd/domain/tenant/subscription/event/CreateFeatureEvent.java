package com.mayanshe.scrmstd.domain.tenant.subscription.event;

import com.mayanshe.scrmstd.shared.contract.DomainEvent;

public record CreateFeatureEvent(
    Long parentId,
    String featureName,
    String displayName,
    String description
) implements DomainEvent {}
