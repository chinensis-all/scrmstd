package com.mayanshe.scrmstd.shared.contract;

public interface CommandHandler<T extends Command<R>, R> {
    R handle(T command);
}
