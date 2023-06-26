package edu.jespinoza.exception;

public class ApplicationException extends Exception {
    private static final long serialVersionUID = -7077805144809974332L;

    /**
     * Constructor
     *
     * @param message The Message
     */
    public ApplicationException(String message) {
        super(message);
    }

    /**
     * Constructor
     *
     * @param message The Message
     * @param cause   The Cause of Exception
     */
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}