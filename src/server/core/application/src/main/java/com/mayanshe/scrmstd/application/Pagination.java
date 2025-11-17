package com.mayanshe.scrmstd.application;

import lombok.Data;
import java.util.List;

@Data
public class Pagination<T> {
    private long total;
    private long pageSize;
    private long page;
    private List<T> list;

    public Pagination(long total, long pageSize, long page, List<T> list) {
        this.total = total;
        this.pageSize = pageSize;
        this.page = page;
        this.list = list;
    }
}
