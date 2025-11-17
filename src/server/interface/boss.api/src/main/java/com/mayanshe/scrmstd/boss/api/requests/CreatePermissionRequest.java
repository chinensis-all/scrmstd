package com.mayanshe.scrmstd.boss.api.requests;

public record CreatePermissionRequest(
        String groupId,
        String permissionName,
        String displayName,
        String description
) {
}
