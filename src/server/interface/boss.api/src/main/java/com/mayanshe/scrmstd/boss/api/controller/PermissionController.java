package com.mayanshe.scrmstd.boss.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mayanshe.scrmstd.application.IDResponse;
import com.mayanshe.scrmstd.application.tenant.command.ActivatePermissionCommand;
import com.mayanshe.scrmstd.application.tenant.command.CreatePermissionCommand;
import com.mayanshe.scrmstd.application.tenant.command.DestroyPermissionCommand;
import com.mayanshe.scrmstd.application.tenant.command.ModifyPermissionCommand;
import com.mayanshe.scrmstd.boss.api.requests.CreatePermissionRequest;
import com.mayanshe.scrmstd.boss.api.requests.ModifyPermissionRequest;
import com.mayanshe.scrmstd.shared.contract.CommandBus;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/permissions")
@SaCheckLogin
public class PermissionController {

    @Resource(name = "simpleCommandBus")
    private CommandBus commandBus;

    @PostMapping
    @SaCheckPermission("permission.create")
    public IDResponse createPermission(@RequestBody CreatePermissionRequest request) {
        CreatePermissionCommand command = new CreatePermissionCommand(
                IdGenerator.fromBase62(request.groupId()),
                request.permissionName(),
                request.displayName(),
                request.description()
        );
        return commandBus.dispatch(command);
    }

    @PutMapping("/{id}")
    @SaCheckPermission("permission.modify")
    public void modifyPermission(@PathVariable String id, @RequestBody ModifyPermissionRequest request) {
        ModifyPermissionCommand command = new ModifyPermissionCommand(
                IdGenerator.fromBase62(id),
                IdGenerator.fromBase62(request.groupId()),
                request.permissionName(),
                request.displayName(),
                request.description()
        );
        commandBus.dispatch(command);
    }

    @PostMapping("/{id}/lock")
    @SaCheckPermission("permission.destroy")
    public void destroyPermission(@PathVariable String id) {
        DestroyPermissionCommand command = new DestroyPermissionCommand(IdGenerator.fromBase62(id));
        commandBus.dispatch(command);
    }

    @DeleteMapping("/{id}/lock")
    @SaCheckPermission("permission.activate")
    public void activatePermission(@PathVariable String id) {
        ActivatePermissionCommand command = new ActivatePermissionCommand(IdGenerator.fromBase62(id));
        commandBus.dispatch(command);
    }
}
