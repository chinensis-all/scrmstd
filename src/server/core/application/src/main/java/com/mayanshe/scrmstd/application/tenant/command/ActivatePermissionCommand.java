package com.mayanshe.scrmstd.application.tenant.command;

import com.mayanshe.scrmstd.shared.contract.Command;

public record ActivatePermissionCommand(
        Long id
) implements Command<Boolean> {
}
