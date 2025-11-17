package com.mayanshe.scrmstd.shared.contract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Aggregate implements Serializable {

    private final transient List<DomainEvent> events = new ArrayList<>();

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addEvent(DomainEvent event) {
        this.events.add(event);
    }

    public List<DomainEvent> getEvents() {
        return Collections.unmodifiableList(this.events);
    }

    public void clearEvents() {
        this.events.clear();
    }
}
