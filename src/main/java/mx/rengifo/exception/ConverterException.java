/**
 * Clase de Excepcion
 */
package mx.rengifo.exception;

/**
 * @author David Rengifo <david@rengifo.mx>
 *
 */
public class ConverterException extends Exception {

	/**
	 * Constante de Serializacion
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor predeterminado
	 */
	public ConverterException() {
		super();
	}
	
	/**
	 * Constructor con mensaje
	 * @param msg
	 */
	public ConverterException(String message) {
		super(message);
	}
	
	/**
	 * Constructor con mensaje y Throwable
	 * @param message
	 * @param cause
	 */
    public ConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor con Throwable
     * @param cause
     */
    public ConverterException(Throwable cause) {
        super(cause);
    }

}
