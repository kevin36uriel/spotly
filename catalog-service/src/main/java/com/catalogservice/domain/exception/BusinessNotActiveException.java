package com.catalogservice.domain.exception;

import com.catalogservice.domain.model.business.BusinessId;

public class BusinessNotActiveException extends RuntimeException {
    public BusinessNotActiveException() {
        super("La cuenta ya esta desactivada no puedes hacer cambios");
    }
}
