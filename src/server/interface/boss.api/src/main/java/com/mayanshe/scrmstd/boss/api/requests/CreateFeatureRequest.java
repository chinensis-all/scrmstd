package com.mayanshe.scrmstd.boss.api.requests;

public record CreateFeatureRequest(
    String parentId,
    String featureName,
    String displayName,
    String description
) {
}
