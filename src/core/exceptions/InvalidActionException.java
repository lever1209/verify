package core.exceptions;

/**
 * Thrown to indicate that an action has been passed an illegal or
 * inappropriate argument.
 *
 * @since 1.0
 */
public class InvalidActionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6982669554758479536L;

	/**
	 * Constructs an {@code InvalidActionException} with no
	 * detail message.
	 */
	public InvalidActionException() {
		super();
	}

	/**
	 * Constructs an {@code InvalidActionException} with the
	 * specified detail message.
	 *
	 * @param s the detail message.
	 */
	public InvalidActionException(String s) {
		super(s);
	}

	/**
	 * Constructs a new exception with the specified detail message and
	 * cause.
	 *
	 * <p>
	 * Note that the detail message associated with {@code cause} is
	 * <i>not</i> automatically incorporated in this exception's detail
	 * message.
	 *
	 * @param message the detail message (which is saved for later retrieval
	 *                by the {@link Throwable#getMessage()} method).
	 * @param cause   the cause (which is saved for later retrieval by the
	 *                {@link Throwable#getCause()} method). (A {@code null} value
	 *                is permitted, and indicates that the cause is nonexistent or
	 *                unknown.)
	 * @since 1.0
	 */
	public InvalidActionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new exception with the specified cause and a detail
	 * message of {@code (cause==null ? null : cause.toString())} (which
	 * typically contains the class and detail message of {@code cause}).
	 * This constructor is useful for exceptions that are little more than
	 * wrappers for other throwables (for example, {@link
	 * java.security.PrivilegedActionException}).
	 *
	 * @param cause the cause (which is saved for later retrieval by the
	 *              {@link Throwable#getCause()} method). (A {@code null} value is
	 *              permitted, and indicates that the cause is nonexistent or
	 *              unknown.)
	 * @since 1.0
	 */
	public InvalidActionException(Throwable cause) {
		super(cause);
	}

}
