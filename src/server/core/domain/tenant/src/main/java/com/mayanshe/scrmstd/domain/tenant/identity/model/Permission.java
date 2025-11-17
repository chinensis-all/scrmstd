package com.mayanshe.scrmstd.domain.tenant.identity.model;

import com.mayanshe.scrmstd.domain.tenant.identity.event.ActivatePermissionEvent;
import com.mayanshe.scrmstd.domain.tenant.identity.event.CreatePermissionEvent;
import com.mayanshe.scrmstd.domain.tenant.identity.event.DestroyPermissionEvent;
import com.mayanshe.scrmstd.domain.tenant.identity.event.ModifyPermissionEvent;
import com.mayanshe.scrmstd.shared.contract.Aggregate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Permission extends Aggregate {
    private Long groupId;
    private String permissionName;
    private String displayName;
    private String description;
    private boolean deleted;

    public static Permission create(Long groupId, String permissionName, String displayName, String description) {
        Permission permission = new Permission();
        permission.groupId = groupId;
        permission.permissionName = permissionName;
        permission.displayName = displayName;
        permission.description = description;
        permission.deleted = false;
        permission.addEvent(new CreatePermissionEvent(groupId, permissionName, displayName, description));
        return permission;
    }

    public void modify(Long groupId, String permissionName, String displayName, String description) {
        this.groupId = groupId;
        this.permissionName = permissionName;
        this.displayName = displayName;
        this.description = description;
        this.addEvent(new ModifyPermissionEvent(groupId, permissionName, displayName, description));
    }

    public void destroy() {
        this.deleted = true;
        this.addEvent(new DestroyPermissionEvent());
    }

    public void activate() {
        this.deleted = false;
        this.addEvent(new ActivatePermissionEvent());
    }
}
