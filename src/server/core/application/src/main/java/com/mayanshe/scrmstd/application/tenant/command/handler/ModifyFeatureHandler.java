package com.mayanshe.scrmstd.application.tenant.command.handler;

import com.mayanshe.scrmstd.application.tenant.command.ModifyFeatureCommand;
import com.mayanshe.scrmstd.domain.tenant.subscription.model.Feature;
import com.mayanshe.scrmstd.domain.tenant.subscription.repo.FeatureRepository;
import com.mayanshe.scrmstd.shared.contract.CommandHandler;
import org.springframework.stereotype.Component;

@Component
public class ModifyFeatureHandler implements CommandHandler<ModifyFeatureCommand, Boolean> {
    private final FeatureRepository featureRepository;

    public ModifyFeatureHandler(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    @Override
    public Boolean handle(ModifyFeatureCommand command) {
        Feature feature = featureRepository.findById(command.id());
        feature.modify(command.parentId(), command.featureName(), command.displayName(), command.description());
        featureRepository.save(feature);
        return true;
    }
}
