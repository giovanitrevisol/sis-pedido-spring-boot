package com.giovanitrevisol.sispedido.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandartError{

    private List<FieldMessage> errors = new ArrayList<>();


    public ValidationError() {
    }

    public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String fildName, String message){
        errors.add(new FieldMessage(fildName, message));
    }
}
