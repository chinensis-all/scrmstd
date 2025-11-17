package com.mayanshe.scrmstd.application;

import com.mayanshe.scrmstd.shared.contract.IdGenerator;
import lombok.Data;

@Data
public class IDResponse {
    private String id;

    public IDResponse(Long id) {
        this.id = IdGenerator.toBase62(id);
    }
}
