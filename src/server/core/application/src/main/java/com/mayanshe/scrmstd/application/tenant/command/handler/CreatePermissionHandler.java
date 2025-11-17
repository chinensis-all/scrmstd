package com.mayanshe.scrmstd.application.tenant.command.handler;

import com.mayanshe.scrmstd.application.IDResponse;
import com.mayanshe.scrmstd.application.tenant.command.CreatePermissionCommand;
import com.mayanshe.scrmstd.domain.tenant.identity.model.Permission;
import com.mayanshe.scrmstd.domain.tenant.identity.repo.PermissionRepository;
import com.mayanshe.scrmstd.shared.contract.CommandHandler;
import org.springframework.stereotype.Component;

@Component
public class CreatePermissionHandler implements CommandHandler<CreatePermissionCommand, IDResponse> {
    private final PermissionRepository permissionRepository;

    public CreatePermissionHandler(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public IDResponse handle(CreatePermissionCommand command) {
        Permission permission = Permission.create(
                command.groupId(),
                command.permissionName(),
                command.displayName(),
                command.description()
        );
        permissionRepository.save(permission);
        return new IDResponse(permission.getId());
    }
}
