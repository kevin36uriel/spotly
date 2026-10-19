package com.catalogservice.domain.model.business;

import java.util.UUID;

public record BusinessId(UUID value) {
    public BusinessId {
        if (value == null) throw new IllegalArgumentException("id es obligatorio");
    }
    public static BusinessId generate() {
        return new BusinessId(UUID.randomUUID());
    }
}
