/**
 * Clase de arranque
 */

package mx.rengifo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.xml.transform.TransformerConfigurationException;

import mx.rengifo.commons.Constantes;
import mx.rengifo.commons.Parameters;
import mx.rengifo.exception.ConverterException;
import mx.rengifo.exception.DownloadException;
import mx.rengifo.tools.XSD2XML;
import mx.rengifo.tools.XStreamConverter;

/**
 * @author David Rengifo <david@rengifo.mx>
 *
 */
public class Start {

	/**
	 * Constructor predeterminado
	 */
	public Start() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Se itera sobre los diferentes schemas enumerados
		for (Constantes.SCHEMA schema : Constantes.SCHEMA.values()) {
			
			//Se prepara el parameters en funcion del schema a procesar
			Parameters p = new Parameters(schema);

			//Descarga el XSD y genera XML
			generateXML(p);

			// Obtain files from specified folder
			File[] xml = new File(p.getFoldernameXML()).listFiles();

			//Convierte el XML a JSON
			convertXML2JSON(p,xml);
			
		}

	}

	
	/**
	 * Lee el XSD de una URL y genera los XMLs
	 * @throws TransformerConfigurationException
	 */
	private static void generateXML(Parameters p) {
		
		XSD2XML xsd2xml = new XSD2XML();
		try {
			// Descarga el XSD de la URL
			xsd2xml.descargar(p.getUrlSchema(), p.getFoldernameXSD(), p.getFilenameXSD());
			
			// Genera los XMLSamples
			xsd2xml.generateXMLSample(p.getFoldernameXSD(), p.getFilenameXSD(), p.getRootElement(), p.getFoldernameXML());
		} catch (DownloadException e) {
			e.printStackTrace();
		} catch (ConverterException e) {
			e.printStackTrace();
		} 
		
	}

	/**
	 * Lee los XML de un arreglo y los convierte a JSON
	 * @param xml
	 */
	private static void convertXML2JSON(Parameters p, File[] xml) {
		
		// Se realiza la conversion de XML a JSON
		XStreamConverter converter = new XStreamConverter();
		FileReader fr = null;
		BufferedReader br = null;

		try {

			for (int i = 0; i < xml.length; i++) {

				// Apertura del fichero y creacion de BufferedReader para poder
				// hacer una lectura comoda (disponer del metodo readLine()).
				File archivo = xml[i];
				fr = new FileReader(archivo);
				br = new BufferedReader(fr);
				String fileNameJSON = archivo.getName().substring(0, archivo.getName().indexOf(".")) + ".json";
				//System.out.println("fileNameJSON = [" + fileNameJSON + "]");
				
				// Lectura del fichero
				StringBuffer xmlBuffer = new StringBuffer("");
		        String linea;
		        while((linea=br.readLine())!=null)
		        	xmlBuffer.append(linea);

		        //Convierte el XML y guarda el archivo JSON
				converter.xml2json(p.getFoldernameJSON(), fileNameJSON, xmlBuffer.toString());

				xmlBuffer=null;

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// En el finally cerramos el fichero, para asegurarnos
			// que se cierra tanto si todo va bien como si salta
			// una excepcion.
			try {
				if (null != br) { br.close(); }
				if (null != fr) { fr.close(); }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
