package com.mayanshe.scrmstd.application.tenant.command.handler;

import com.mayanshe.scrmstd.application.tenant.command.DestroyPermissionCommand;
import com.mayanshe.scrmstd.domain.tenant.identity.model.Permission;
import com.mayanshe.scrmstd.domain.tenant.identity.repo.PermissionRepository;
import com.mayanshe.scrmstd.shared.contract.CommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DestroyPermissionHandler implements CommandHandler<DestroyPermissionCommand, Boolean> {
    private final PermissionRepository permissionRepository;

    public DestroyPermissionHandler(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Boolean handle(DestroyPermissionCommand command) {
        Permission permission = permissionRepository.findById(command.id()).orElseThrow();
        permission.destroy();
        permissionRepository.save(permission);
        return true;
    }
}
