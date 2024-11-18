package fr.cyu.coffeeclasses.vanilla.database.exception;

public class DataNonsenseException extends RuntimeException {
	public DataNonsenseException(String message, Throwable cause) {
		super(message, cause);
	}
	public DataNonsenseException(String message) {
		super(message);
	}
}
