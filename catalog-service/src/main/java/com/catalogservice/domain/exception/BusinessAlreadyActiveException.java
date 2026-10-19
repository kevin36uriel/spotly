package com.catalogservice.domain.exception;

import com.catalogservice.domain.model.business.BusinessId;

public class BusinessAlreadyActiveException extends RuntimeException {
    public BusinessAlreadyActiveException(BusinessId businessId) {
        super("La cuenta con ID " + businessId + " ya esta desactivada ");
    }
}
