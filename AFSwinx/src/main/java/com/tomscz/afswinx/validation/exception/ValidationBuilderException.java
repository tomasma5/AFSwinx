package com.tomscz.afswinx.validation.exception;

import javax.print.attribute.standard.Severity;

/**
 * This exception is thrown when error is performed during building validator. 
 * There are severity if error. 
 * Critical  - validator is not working.
 * Major - validator is working, but some of parameters to create was not successfuly created.
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public class ValidationBuilderException extends Exception{

    private Severity severity;
    
    private static final long serialVersionUID = 1L;

    public ValidationBuilderException(String message, Severity severity){
        super(message);
        this.severity = severity;
    }
    
    public Severity getSeverity(){
        return this.severity;
    }
    
    
}
