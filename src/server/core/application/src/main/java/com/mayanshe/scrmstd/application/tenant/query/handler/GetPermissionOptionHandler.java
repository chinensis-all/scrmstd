package com.mayanshe.scrmstd.application.tenant.query.handler;

import com.mayanshe.scrmstd.application.OptionDto;
import com.mayanshe.scrmstd.application.query.repo.PermissionQueryRepository;
import com.mayanshe.scrmstd.application.tenant.query.PermissionOptionQuery;
import com.mayanshe.scrmstd.shared.contract.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetPermissionOptionHandler implements QueryHandler<PermissionOptionQuery, List<OptionDto>> {
    private final PermissionQueryRepository permissionQueryRepository;

    public GetPermissionOptionHandler(PermissionQueryRepository permissionQueryRepository) {
        this.permissionQueryRepository = permissionQueryRepository;
    }

    @Override
    public List<OptionDto> handle(PermissionOptionQuery query) {
        return permissionQueryRepository.listAsOption(query);
    }
}
