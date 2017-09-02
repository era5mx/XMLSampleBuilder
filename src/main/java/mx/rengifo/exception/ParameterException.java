/**
 * Clase de Excepcion
 */
package mx.rengifo.exception;

/**
 * @author David Rengifo <david@rengifo.mx>
 *
 */
public class ParameterException extends Exception {

	/**
	 * Constante de Serializacion
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor predeterminado
	 */
	public ParameterException() {
		super();
	}
	
	/**
	 * Constructor con mensaje
	 * @param msg
	 */
	public ParameterException(String message) {
		super(message);
	}
	
	/**
	 * Constructor con mensaje y Throwable
	 * @param message
	 * @param cause
	 */
    public ParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor con Throwable
     * @param cause
     */
    public ParameterException(Throwable cause) {
        super(cause);
    }

}
