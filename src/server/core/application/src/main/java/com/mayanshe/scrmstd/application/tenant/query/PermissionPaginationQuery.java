package com.mayanshe.scrmstd.application.tenant.query;

import com.mayanshe.scrmstd.application.Pagination;
import com.mayanshe.scrmstd.application.tenant.query.dto.PermissionDto;
import com.mayanshe.scrmstd.shared.contract.Query;

public record PermissionPaginationQuery(
        Integer page,
        Integer pageSize
) implements Query<Pagination<PermissionDto>> {
}
