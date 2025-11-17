package com.mayanshe.scrmstd.application.tenant.command.handler;

import com.mayanshe.scrmstd.application.tenant.command.CreateFeatureCommand;
import com.mayanshe.scrmstd.domain.tenant.subscription.model.Feature;
import com.mayanshe.scrmstd.domain.tenant.subscription.repo.FeatureRepository;
import com.mayanshe.scrmstd.shared.contract.CommandHandler;
import com.mayanshe.scrmstd.shared.contract.IDResponse;
import org.springframework.stereotype.Component;

@Component
public class CreateFeatureHandler implements CommandHandler<CreateFeatureCommand, IDResponse> {
    private final FeatureRepository featureRepository;

    public CreateFeatureHandler(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    @Override
    public IDResponse handle(CreateFeatureCommand command) {
        Feature feature = Feature.create(command.parentId(), command.featureName(), command.displayName(), command.description());
        featureRepository.save(feature);
        return new IDResponse(feature.getId());
    }
}
