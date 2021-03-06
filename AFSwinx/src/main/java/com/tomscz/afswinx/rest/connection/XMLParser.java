package com.tomscz.afswinx.rest.connection;

import com.tomscz.afswinx.common.ParameterMissingException;
import org.w3c.dom.Document;

/**
 * This interface specify behavior of XMLParses.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public interface XMLParser {

    /**
     * This method parse XML document.
     * 
     * @param documentToParse document which should be parsed
     * @return data of document. Typically as object of generic type.
     * @throws ParameterMissingException if some parameters during expression evaluation is missing
     */
    public <T> T parseDocument(Document documentToParse) throws ParameterMissingException;

}
