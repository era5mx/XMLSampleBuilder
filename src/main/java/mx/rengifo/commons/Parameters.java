/**
 * 
 */
package mx.rengifo.commons;

import mx.rengifo.commons.Constantes.SCHEMA;

/**
 * @author David Rengifo <david@rengifo.mx>
 *
 */
public class Parameters {
	
	String urlSchema;
	String foldernameXSD;
	String filenameXSD;
	String foldernameXML;
	String foldernameJSON;
	String rootElement;

	/**
	 * Constructor del Parameters con el parametro schema
	 */
	public Parameters(SCHEMA schema) {
		
		switch (schema) {
		case CFDI:
			urlSchema = Constantes.URL_SCHEMA_CFDI;
			foldernameXSD = Constantes.FOLDERNAME_XSD_CFDI;
			filenameXSD = Constantes.FILENAME_XSD_CFDI;
			foldernameXML = Constantes.FOLDERNAME_XML_CFDI;
			foldernameJSON = Constantes.FOLDERNAME_JSON_CFDI;
			rootElement = Constantes.ROOT_ELEMENT_CFDI;
			break;
		case RETENCIONES:
			urlSchema = Constantes.URL_SCHEMA_RETENCIONES;
			foldernameXSD = Constantes.FOLDERNAME_XSD_RETENCIONES;
			filenameXSD = Constantes.FILENAME_XSD_RETENCIONES;
			foldernameXML = Constantes.FOLDERNAME_XML_RETENCIONES;
			foldernameJSON = Constantes.FOLDERNAME_JSON_RETENCIONES;
			rootElement = Constantes.ROOT_ELEMENT_RETENCIONES;
			break;
		case PAGOS:
			urlSchema = Constantes.URL_SCHEMA_PAGOS;
			foldernameXSD = Constantes.FOLDERNAME_XSD_PAGOS;
			filenameXSD = Constantes.FILENAME_XSD_PAGOS;
			foldernameXML = Constantes.FOLDERNAME_XML_PAGOS;
			foldernameJSON = Constantes.FOLDERNAME_JSON_PAGOS;
			rootElement = Constantes.ROOT_ELEMENT_PAGOS;
			break;
		default:
			break;
		}
		
	}

	/**
	 * @return the urlSchema
	 */
	public String getUrlSchema() {
		return urlSchema;
	}

	/**
	 * @return the foldernameXSD
	 */
	public String getFoldernameXSD() {
		return foldernameXSD;
	}

	/**
	 * @return the filenameXSD
	 */
	public String getFilenameXSD() {
		return filenameXSD;
	}

	/**
	 * @return the foldernameXML
	 */
	public String getFoldernameXML() {
		return foldernameXML;
	}

	/**
	 * @return the rootElement
	 */
	public String getRootElement() {
		return rootElement;
	}

	/**
	 * @return the foldernameJSON
	 */
	public String getFoldernameJSON() {
		return foldernameJSON;
	}	

}
