package com.mayanshe.scrmstd.application.tenant.query.handler;

import com.mayanshe.scrmstd.application.Pagination;
import com.mayanshe.scrmstd.application.query.repo.PermissionQueryRepository;
import com.mayanshe.scrmstd.application.tenant.query.PermissionPaginationQuery;
import com.mayanshe.scrmstd.application.tenant.query.dto.PermissionDto;
import com.mayanshe.scrmstd.shared.contract.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetPermissionPaginationHandler implements QueryHandler<PermissionPaginationQuery, Pagination<PermissionDto>> {
    private final PermissionQueryRepository permissionQueryRepository;

    public GetPermissionPaginationHandler(PermissionQueryRepository permissionQueryRepository) {
        this.permissionQueryRepository = permissionQueryRepository;
    }

    @Override
    public Pagination<PermissionDto> handle(PermissionPaginationQuery query) {
        return permissionQueryRepository.pagination(query);
    }
}
