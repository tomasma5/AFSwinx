package com.tomscz.afserver.manager.exceptions;

import javax.ws.rs.core.Response.Status;

public class BusinessException extends Exception{

    private static final long serialVersionUID = 1L;

    public BusinessException(Status status){
        this.status = status;
    }
    
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
}
