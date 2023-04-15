package com.softcaribbean.hulkstore.api.exceptions;

import org.hibernate.ObjectNotFoundException;

import java.io.Serial;

public class NotProductException extends ObjectNotFoundException {

    @Serial
    private static final long serialVersionUID = -7304241579563712912L;

    public NotProductException(Object identifier, String entityName) {
        super(identifier, entityName);
    }

    public NotProductException(String entityName, Object identifier) {
        super(entityName, identifier);
    }
}
