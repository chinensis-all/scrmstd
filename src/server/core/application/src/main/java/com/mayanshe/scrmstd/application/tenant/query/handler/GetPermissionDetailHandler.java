package com.mayanshe.scrmstd.application.tenant.query.handler;

import com.mayanshe.scrmstd.application.query.repo.PermissionQueryRepository;
import com.mayanshe.scrmstd.application.tenant.query.PermissionDetailQuery;
import com.mayanshe.scrmstd.application.tenant.query.dto.PermissionDto;
import com.mayanshe.scrmstd.shared.contract.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetPermissionDetailHandler implements QueryHandler<PermissionDetailQuery, PermissionDto> {
    private final PermissionQueryRepository permissionQueryRepository;

    public GetPermissionDetailHandler(PermissionQueryRepository permissionQueryRepository) {
        this.permissionQueryRepository = permissionQueryRepository;
    }

    @Override
    public PermissionDto handle(PermissionDetailQuery query) {
        return permissionQueryRepository.findById(query.id()).orElse(null);
    }
}
