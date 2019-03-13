package com.doms.automation.utils;

/**
 *
 * @author Dhanesh_Raj
 */
public class ApplicationException extends Exception{

	private static final long serialVersionUID = 1L;

    public ApplicationException(String message) {
        super(message);
    }

    /**
     * Constructs a DAOException with the given root cause.
     * @param cause The root cause of the ApplicationException.
     */
    public ApplicationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a DAOException with the given detail message and root cause.
     * @param message The detail message of the ApplicationException.
     * @param cause The root cause of the ApplicationException.
     */
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}

