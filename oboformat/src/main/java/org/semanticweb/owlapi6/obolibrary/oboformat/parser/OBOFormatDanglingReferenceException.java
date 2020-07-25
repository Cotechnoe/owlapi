package org.semanticweb.owlapi6.obolibrary.oboformat.parser;

/**
 * The Class OBOFormatDanglingReferenceException.
 */
public class OBOFormatDanglingReferenceException extends OBOFormatException {

    /**
     * Instantiates a new OBO format dangling reference exception.
     */
    public OBOFormatDanglingReferenceException() {
        super();
    }

    /**
     * Instantiates a new OBO format dangling reference exception.
     *
     * @param message the message
     */
    public OBOFormatDanglingReferenceException(String message) {
        super(message);
    }

    /**
     * Instantiates a new OBO format dangling reference exception.
     *
     * @param e the e
     */
    public OBOFormatDanglingReferenceException(Throwable e) {
        super(e);
    }

    /**
     * Instantiates a new OBO format dangling reference exception.
     *
     * @param message the message
     * @param e the e
     */
    public OBOFormatDanglingReferenceException(String message, Throwable e) {
        super(message, e);
    }
}
