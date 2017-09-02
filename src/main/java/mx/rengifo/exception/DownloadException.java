/**
 * Clase de Excepcion
 */
package mx.rengifo.exception;

/**
 * @author David Rengifo <david@rengifo.mx>
 *
 */
public class DownloadException extends Exception {

	/**
	 * Constante de Serializacion
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor predeterminado
	 */
	public DownloadException() {
		super();
	}
	
	/**
	 * Constructor con mensaje
	 * @param msg
	 */
	public DownloadException(String message) {
		super(message);
	}
	
	/**
	 * Constructor con mensaje y Throwable
	 * @param message
	 * @param cause
	 */
    public DownloadException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor con Throwable
     * @param cause
     */
    public DownloadException(Throwable cause) {
        super(cause);
    }

}
