package com.mayanshe.scrmstd.boss.api.requests;

public record ModifyPermissionRequest(
        String groupId,
        String permissionName,
        String displayName,
        String description
) {
}
