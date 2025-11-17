package com.mayanshe.scrmstd.application.tenant.query.dto;

import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import lombok.Data;

@Data
public class FeatureDto {
    private Long id;
    private Long parentId;
    private String featureName;
    private String displayName;
    private String description;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;

    public String id() {
        return IdGenerator.toBase62(this.id);
    }

    public String parentId() {
        return IdGenerator.toBase62(this.parentId);
    }
}
