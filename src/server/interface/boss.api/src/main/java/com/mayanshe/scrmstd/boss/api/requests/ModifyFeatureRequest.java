package com.mayanshe.scrmstd.boss.api.requests;

public record ModifyFeatureRequest(
    String parentId,
    String featureName,
    String displayName,
    String description
) {
}
