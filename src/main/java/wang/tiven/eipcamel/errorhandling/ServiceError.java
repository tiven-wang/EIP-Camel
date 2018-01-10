package wang.tiven.eipcamel.errorhandling;

public class ServiceError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6000749494745019786L;

	public ServiceError() {
		super();
	}

	public ServiceError(String message) {
		super(message);
	}
	
}
