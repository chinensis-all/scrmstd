package com.mayanshe.scrmstd.shared.contract;

public interface QueryHandler<T extends Query<R>, R> {
    R handle(T query);
}
