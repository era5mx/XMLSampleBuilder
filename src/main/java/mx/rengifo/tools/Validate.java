/**
 * Validador
 */
package mx.rengifo.tools;

import org.apache.commons.lang3.StringUtils;

import mx.rengifo.exception.ParameterException;

/**
 * @author David Rengifo <david@rengifo.mx>
 *
 */
public class Validate {

	/**
	 * Constructor predeterminado privado para evitar la instanciacion de la clase
	 */
	public Validate() {
	}

	/**
	 * Valida el path y el name
	 * @param path
	 * @param name
	 */
	public static void validaPathAndName(String path, String name) throws ParameterException {
		boolean valido=true;
		StringBuffer sb = new StringBuffer("");
		if(StringUtils.isBlank(path)) {
			sb.append("El path recibido es blanco o nulo. ");
			valido=false;
		};
		if(StringUtils.isBlank(name)) {
			sb.append("El name recibido es blanco o nulo. ");
			valido=false;
		};
		if(!valido) new ParameterException();
	}

}
