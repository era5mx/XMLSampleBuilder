/**
 * Clase de constantes
 */
package mx.rengifo.commons;

/**
 * @author David Rengifo <david@rengifo.mx>
 *
 */
public class Constantes {

	/**
	 * Constructor predeterminado privado para inhibir la instanciacion de la clase
	 */
	private Constantes() {
	}
	
	/**
	 * @author David Rengifo <david@rengifo.mx>
	 *
	 */
	public static enum SCHEMA {
		CFDI("CFDI"),
		RETENCIONES("RETENCIONES"),
		PAGOS("PAGOS");

	    private final String schema;

	    /**
	     * @param text
	     */
	    private SCHEMA(final String text) {
	        this.schema = text;
	    }

	    /* (non-Javadoc)
	     * @see java.lang.Enum#toString()
	     */
	    @Override
	    public String toString() {
	        return schema;
	    }

		/**
		 * @return
		 */
		public String getSchema() {
			return schema;
		}

	}
	
	public static final String URL_SCHEMA_CFDI = "http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd";
	public static final String URL_SCHEMA_RETENCIONES = "http://www.sat.gob.mx/esquemas/retencionpago/1/retencionpagov1.xsd";
	public static final String URL_SCHEMA_PAGOS = "http://www.sat.gob.mx/sitio_internet/cfd/pagos/pagos10.xsd";

	public static final String ROOT_ELEMENT_CFDI = "Comprobante";
	public static final String ROOT_ELEMENT_RETENCIONES = "Retenciones";
	public static final String ROOT_ELEMENT_PAGOS = "Pagos";
	
	public static final String FOLDERNAME_XSD_CFDI = "C:\\Temp\\ComprobanteXSD\\";
	public static final String FOLDERNAME_XSD_RETENCIONES = "C:\\Temp\\RetencionesXSD\\";
	public static final String FOLDERNAME_XSD_PAGOS = "C:\\Temp\\PagosSampleXSD\\";
	
	public static final String FILENAME_XSD_CFDI = "cfdv33.xsd";
	public static final String FILENAME_XSD_RETENCIONES = "retencionpagov1.xsd";
	public static final String FILENAME_XSD_PAGOS = "pagos10.xsd";
	
	public static final String FOLDERNAME_XML_CFDI = "C:\\Temp\\ComprobanteSampleXML\\";
	public static final String FOLDERNAME_XML_RETENCIONES = "C:\\Temp\\RetencionesSampleXML\\";
	public static final String FOLDERNAME_XML_PAGOS = "C:\\Temp\\PagosSampleXML\\";
	
	public static final String FOLDERNAME_JSON_CFDI = "C:\\Temp\\ComprobanteJSON\\";
	public static final String FOLDERNAME_JSON_RETENCIONES = "C:\\Temp\\RetencionesJSON\\";
	public static final String FOLDERNAME_JSON_PAGOS = "C:\\Temp\\PagosJSON\\";
	
//	public static final String FILENAME_JSON_CFDI = "cfdv33.json";
//	public static final String FILENAME_JSON_RETENCIONES = "retencionpagov1.json";
//	public static final String FILENAME_JSON_PAGOS = "pagos10.json";

}
