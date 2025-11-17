package com.mayanshe.scrmstd.application.tenant.command;

import com.mayanshe.scrmstd.shared.contract.Command;
import com.mayanshe.scrmstd.shared.contract.IDResponse;

public record CreateFeatureCommand(
    Long parentId,
    String featureName,
    String displayName,
    String description
) implements Command<IDResponse> {
}
