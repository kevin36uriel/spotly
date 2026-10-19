package com.catalogservice.domain.exception;

import com.catalogservice.domain.model.business.BusinessId;

public class BusinessAlreadyInactiveException extends RuntimeException {
    public BusinessAlreadyInactiveException(BusinessId businessId) {
        super("La cuenta con ID " + businessId + " ya esta desactivada ");
    }
}
