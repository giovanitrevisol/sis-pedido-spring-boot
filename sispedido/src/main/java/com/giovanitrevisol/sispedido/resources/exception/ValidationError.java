package com.giovanitrevisol.sispedido.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandartError{

    private List<FieldMessage> errors = new ArrayList<>();


    public ValidationError() {
    }

    public ValidationError(Integer status, String msg, Long timeStamp) {
        super(status, msg, timeStamp);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String fildName, String message){
        errors.add(new FieldMessage(fildName, message));
    }
}
