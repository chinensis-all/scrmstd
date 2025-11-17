package com.mayanshe.scrmstd.application.tenant.command;

import com.mayanshe.scrmstd.application.IDResponse;
import com.mayanshe.scrmstd.shared.contract.Command;

public record CreatePermissionCommand(
        Long groupId,
        String permissionName,
        String displayName,
        String description
) implements Command<IDResponse> {
}
