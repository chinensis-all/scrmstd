package com.mayanshe.scrmstd.application.tenant.command;

import com.mayanshe.scrmstd.shared.contract.Command;

public record ModifyPermissionCommand(
        Long id,
        Long groupId,
        String permissionName,
        String displayName,
        String description
) implements Command<Boolean> {
}
