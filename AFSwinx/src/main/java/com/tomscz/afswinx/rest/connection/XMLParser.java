package com.tomscz.afswinx.rest.connection;

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
     * @param documentToParse
     * @return data of document. Typically as object of generic type.
     */
    public <T> T parseDocument(Document documentToParse);

}
