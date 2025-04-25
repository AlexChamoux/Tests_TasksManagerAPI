package com.example.task.model;

import java.util.UUID;

public class Task {
    private final UUID id;
    private final String description;
    private String state;

    public Task(String description) {
        this.id = UUID.randomUUID();
        this.description = description;
        this.state = "en cours";
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getState() {
        return state;
    }

    public void setStateOver() {
        this.state = "terminé";
    }
}
