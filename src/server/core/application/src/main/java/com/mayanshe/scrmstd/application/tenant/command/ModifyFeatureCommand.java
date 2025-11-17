package com.mayanshe.scrmstd.application.tenant.command;

import com.mayanshe.scrmstd.shared.contract.Command;

public record ModifyFeatureCommand(
    Long id,
    Long parentId,
    String featureName,
    String displayName,
    String description
) implements Command<Boolean> {
}
