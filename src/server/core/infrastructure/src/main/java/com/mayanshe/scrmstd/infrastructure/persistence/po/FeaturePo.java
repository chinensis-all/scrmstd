package com.mayanshe.scrmstd.infrastructure.persistence.po;

import lombok.Data;

@Data
public class FeaturePo {
    private Long id;
    private Long parentId;
    private String featureName;
    private String displayName;
    private String description;
    private Long createdAt;
    private Long updatedAt;
    private Long deletedAt;
}
