/**
 * I often need to convert XML to JSON. Here’s a simple method that uses XStream and Jettison
 * Ref: 
 * 		https://dzone.com/articles/crazy-fast-way-convert-xml
 * 		https://www.programcreek.com/java-api-examples/index.php?api=com.thoughtworks.xstream.io.xml.XppReader
 */
package mx.rengifo.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.copy.HierarchicalStreamCopier;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.xml.XppReader;

import mx.rengifo.exception.ConverterException;
import mx.rengifo.exception.ParameterException;

public class XStreamConverter {

	/**
	 * Convierte el XML en JSON
	 * 
	 * @param xml
	 * @throws ConverterException
	 */
	public void xml2json(String pathJSON, String nameJSON, String xml) throws ConverterException {

		HierarchicalStreamReader sourceReader = null;
		try {
			sourceReader = new XppReader(new StringReader(xml), XmlPullParserFactory.newInstance().newPullParser());
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			throw new ConverterException("Ocurrio una XmlPullParserException", e);
		}

		StringWriter buffer = new StringWriter();
		JettisonMappedXmlDriver jettisonDriver = new JettisonMappedXmlDriver();
		jettisonDriver.createWriter(buffer);
		HierarchicalStreamWriter destinationWriter = jettisonDriver.createWriter(buffer);

		// Por formalidad se debe validar que sourceReader no sea null, aunque nunca
		// deberia suceder
		if (sourceReader != null) {
			HierarchicalStreamCopier copier = new HierarchicalStreamCopier();
			copier.copy(sourceReader, destinationWriter);
		}

		System.out.println(buffer.toString());
		saveJson(pathJSON, nameJSON, buffer.toString());

	}

	/**
	 * Guarda el JSON en el archivo y ruta indicadas
	 * 
	 * @param pathJSON
	 * @param nameJSON
	 * @param json
	 */
	private void saveJson(String pathJSON, String nameJSON, String json) throws ConverterException {
		
		//Create la carpeta
		File folder = new File(pathJSON);
		if (!folder.exists()) folder.mkdir(); 

		FileWriter fichero = null;
		PrintWriter pw = null;

		try {
			Validate.validaPathAndName(pathJSON, nameJSON);
			fichero = new FileWriter(pathJSON + nameJSON);
			pw = new PrintWriter(fichero);
			pw.println(json);

		} catch (ParameterException e) {
			e.printStackTrace();
			throw new ConverterException("Ocurrio una ParameterException", e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ConverterException("Ocurrio una IOException", e);
		} finally {
			try {
				if (null != fichero)
					fichero.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}