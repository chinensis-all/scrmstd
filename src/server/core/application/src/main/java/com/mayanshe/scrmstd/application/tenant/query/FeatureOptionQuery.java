package com.mayanshe.scrmstd.application.tenant.query;

import com.mayanshe.scrmstd.application.OptionDto;
import com.mayanshe.scrmstd.shared.contract.Query;

import java.util.List;

public record FeatureOptionQuery() implements Query<List<OptionDto>> {
}
