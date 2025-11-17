package com.mayanshe.scrmstd.domain.tenant.subscription.model;

import com.mayanshe.scrmstd.domain.tenant.subscription.event.ActivateFeatureEvent;
import com.mayanshe.scrmstd.domain.tenant.subscription.event.CreateFeatureEvent;
import com.mayanshe.scrmstd.domain.tenant.subscription.event.DestroyFeatureEvent;
import com.mayanshe.scrmstd.domain.tenant.subscription.event.ModifyFeatureEvent;
import com.mayanshe.scrmstd.shared.contract.Aggregate;
import lombok.Getter;

@Getter
public class Feature extends Aggregate<Long> {
    private Long parentId;
    private String featureName;
    private String displayName;
    private String description;
    private boolean deleted;

    public static Feature create(Long parentId, String featureName, String displayName, String description) {
        Feature feature = new Feature();
        feature.parentId = parentId;
        feature.featureName = featureName;
        feature.displayName = displayName;
        feature.description = description;
        feature.addEvent(new CreateFeatureEvent(parentId, featureName, displayName, description));
        return feature;
    }

    public void modify(Long parentId, String featureName, String displayName, String description) {
        this.parentId = parentId;
        this.featureName = featureName;
        this.displayName = displayName;
        this.description = description;
        this.addEvent(new ModifyFeatureEvent(parentId, featureName, displayName, description));
    }

    public void destroy() {
        this.deleted = true;
        this.addEvent(new DestroyFeatureEvent());
    }

    public void activate() {
        this.deleted = false;
        this.addEvent(new ActivateFeatureEvent());
    }
}
