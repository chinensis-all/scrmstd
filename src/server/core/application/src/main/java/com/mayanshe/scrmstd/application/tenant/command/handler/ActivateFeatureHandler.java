package com.mayanshe.scrmstd.application.tenant.command.handler;

import com.mayanshe.scrmstd.application.tenant.command.ActivateFeatureCommand;
import com.mayanshe.scrmstd.domain.tenant.subscription.model.Feature;
import com.mayanshe.scrmstd.domain.tenant.subscription.repo.FeatureRepository;
import com.mayanshe.scrmstd.shared.contract.CommandHandler;
import org.springframework.stereotype.Component;

@Component
public class ActivateFeatureHandler implements CommandHandler<ActivateFeatureCommand, Boolean> {
    private final FeatureRepository featureRepository;

    public ActivateFeatureHandler(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    @Override
    public Boolean handle(ActivateFeatureCommand command) {
        Feature feature = featureRepository.findById(command.id());
        feature.activate();
        featureRepository.save(feature);
        return true;
    }
}
