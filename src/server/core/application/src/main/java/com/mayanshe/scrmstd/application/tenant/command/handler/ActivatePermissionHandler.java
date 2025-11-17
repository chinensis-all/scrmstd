package com.mayanshe.scrmstd.application.tenant.command.handler;

import com.mayanshe.scrmstd.application.tenant.command.ActivatePermissionCommand;
import com.mayanshe.scrmstd.domain.tenant.identity.model.Permission;
import com.mayanshe.scrmstd.domain.tenant.identity.repo.PermissionRepository;
import com.mayanshe.scrmstd.shared.contract.CommandHandler;
import org.springframework.stereotype.Component;

@Component
public class ActivatePermissionHandler implements CommandHandler<ActivatePermissionCommand, Boolean> {
    private final PermissionRepository permissionRepository;

    public ActivatePermissionHandler(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Boolean handle(ActivatePermissionCommand command) {
        Permission permission = permissionRepository.findById(command.id()).orElseThrow();
        permission.activate();
        permissionRepository.save(permission);
        return true;
    }
}
