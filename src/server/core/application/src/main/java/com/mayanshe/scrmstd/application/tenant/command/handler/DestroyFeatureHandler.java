package com.mayanshe.scrmstd.application.tenant.command.handler;

import com.mayanshe.scrmstd.application.tenant.command.DestroyFeatureCommand;
import com.mayanshe.scrmstd.domain.tenant.subscription.model.Feature;
import com.mayanshe.scrmstd.domain.tenant.subscription.repo.FeatureRepository;
import com.mayanshe.scrmstd.shared.contract.CommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DestroyFeatureHandler implements CommandHandler<DestroyFeatureCommand, Boolean> {
    private final FeatureRepository featureRepository;

    public DestroyFeatureHandler(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    @Override
    public Boolean handle(DestroyFeatureCommand command) {
        Feature feature = featureRepository.findById(command.id());
        feature.destroy();
        featureRepository.save(feature);
        return true;
    }
}
