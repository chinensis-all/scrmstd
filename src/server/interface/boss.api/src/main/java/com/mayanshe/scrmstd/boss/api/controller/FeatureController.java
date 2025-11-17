package com.mayanshe.scrmstd.boss.api.controller;

import com.mayanshe.scrmstd.application.tenant.command.ActivateFeatureCommand;
import com.mayanshe.scrmstd.application.tenant.command.CreateFeatureCommand;
import com.mayanshe.scrmstd.application.tenant.command.DestroyFeatureCommand;
import com.mayanshe.scrmstd.application.tenant.command.ModifyFeatureCommand;
import com.mayanshe.scrmstd.boss.api.requests.CreateFeatureRequest;
import com.mayanshe.scrmstd.boss.api.requests.ModifyFeatureRequest;
import com.mayanshe.scrmstd.shared.contract.CommandBus;
import com.mayanshe.scrmstd.shared.contract.IDResponse;
import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import com.mayanshe.scrmstd.shared.contract.QueryBus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/features")
public class FeatureController {
    @Resource(name = "simpleCommandBus")
    private CommandBus commandBus;

    @Resource(name = "simpleQueryBus")
    private QueryBus queryBus;

    @PostMapping
    public IDResponse createFeature(@RequestBody CreateFeatureRequest request) {
        return commandBus.dispatch(new CreateFeatureCommand(
                IdGenerator.fromBase62(request.parentId()),
                request.featureName(),
                request.displayName(),
                request.description()
        ));
    }

    @PutMapping("/{id}")
    public void modifyFeature(@PathVariable String id, @RequestBody ModifyFeatureRequest request) {
        commandBus.dispatch(new ModifyFeatureCommand(
                IdGenerator.fromBase62(id),
                IdGenerator.fromBase62(request.parentId()),
                request.featureName(),
                request.displayName(),
                request.description()
        ));
    }

    @PostMapping("/{id}/lock")
    public void destroyFeature(@PathVariable String id) {
        commandBus.dispatch(new DestroyFeatureCommand(IdGenerator.fromBase62(id)));
    }

    @DeleteMapping("/{id}/lock")
    public void activateFeature(@PathVariable String id) {
        commandBus.dispatch(new ActivateFeatureCommand(IdGenerator.fromBase62(id)));
    }
}
