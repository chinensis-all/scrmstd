package com.mayanshe.scrmstd.application.tenant.command.handler;

import com.mayanshe.scrmstd.application.tenant.command.ModifyPermissionCommand;
import com.mayanshe.scrmstd.domain.tenant.identity.model.Permission;
import com.mayanshe.scrmstd.domain.tenant.identity.repo.PermissionRepository;
import com.mayanshe.scrmstd.shared.contract.CommandHandler;
import org.springframework.stereotype.Component;

@Component
public class ModifyPermissionHandler implements CommandHandler<ModifyPermissionCommand, Boolean> {
    private final PermissionRepository permissionRepository;

    public ModifyPermissionHandler(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Boolean handle(ModifyPermissionCommand command) {
        Permission permission = permissionRepository.findById(command.id()).orElseThrow();
        permission.modify(
                command.groupId(),
                command.permissionName(),
                command.displayName(),
                command.description()
        );
        permissionRepository.save(permission);
        return true;
    }
}
