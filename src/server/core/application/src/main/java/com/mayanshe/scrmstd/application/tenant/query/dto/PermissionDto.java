package com.mayanshe.scrmstd.application.tenant.query.dto;

import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import lombok.Data;

@Data
public class PermissionDto {
    private Long id;
    private Long groupId;
    private String permissionName;
    private String displayName;
    private String description;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;

    public String id() {
        return IdGenerator.toBase62(this.id);
    }

    public String groupId() {
        return IdGenerator.toBase62(this.groupId);
    }
}
