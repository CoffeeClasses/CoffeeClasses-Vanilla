package fr.cyu.coffeeclasses.vanilla.database.exception;

public class DataAccessException extends RuntimeException {
	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}
	public DataAccessException(String message) {
		super(message);
	}
}