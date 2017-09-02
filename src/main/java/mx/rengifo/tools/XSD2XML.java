/**
 * Ref: 
 * http://www.lostinsoftware.com/2015/03/parsing-xsd-schema-files-with-java/
 * https://github.com/elbuo8/Sample-XSD-to-XML-Batch-Generator/blob/master/Runner.java
 */
package mx.rengifo.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.xs.XSImplementation;
import org.apache.xerces.xs.XSLoader;
import org.apache.xerces.xs.XSModel;
import org.apache.xerces.dom.DOMInputImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.LSInput;
import org.xml.sax.SAXException;

import jlibs.xml.sax.XMLDocument;
import jlibs.xml.xsd.XSInstance;
import mx.rengifo.exception.ConverterException;
import mx.rengifo.exception.DownloadException;
import mx.rengifo.exception.ParameterException;

/**
 * Generador de XML Sample a partir de un XSD cargado desde una URL publica
 * @author David Rengifo <david@rengifo.mx>
 *
 */
public class XSD2XML {

	/**
	 * Constructor predeterminado
	 */
	public XSD2XML() {
	}
	
	/**
	 * Prepara el path donde se guarda el XSD y Descarga el XSD desde la URL indicada
	 * @param url
	 * @param pathXSD
	 * @param nameXSD
	 * @return La ruta donde se guardo localmente el XSD
	 * @throws DownloadException
	 */
	public void descargar(String url, String pathXSD, String nameXSD) throws DownloadException {

		InputStream inputStream = null;
		OutputStream outputStream = null;
		String ficheroDestino = null;
		
		//Create la carpeta
		File folder = new File(pathXSD);
		if (!folder.exists()) folder.mkdir(); 
		
		try {
			URL ficheroUrl = new URL(url);
			ficheroDestino = pathXSD.concat(nameXSD);
			inputStream = ficheroUrl.openStream();
			outputStream = new FileOutputStream(ficheroDestino); // path y nombre del nuevo fichero creado

			byte[] b = new byte[2048];
			int longitud;

			while ((longitud = inputStream.read(b)) != -1) {
				outputStream.write(b, 0, longitud);
			}
								
		} catch(MalformedURLException e) {
			e.printStackTrace();
			throw new DownloadException("Ocurrio una MalformedURLException al intentar acceder a [" + url + "]", e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new DownloadException("Ocurrio una IOException al crear [" + ficheroDestino + "]", e);
		}
		finally {
			try {
				if(inputStream!=null) inputStream.close(); // Cerramos la conexion entrada
				if(outputStream!=null) outputStream.close(); // Cerramos la conexion salida
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Genera XML a partir del XSD
	 * @param pathXSD
	 * @param nameXSD
	 * @throws ConverterException
	 */
	public void generateXMLSample(String pathXSD, String nameXSD, String elementRoot, String folderName) throws ConverterException {
		
        try {
        	
    		//Create la carpeta
    		File folder = new File(folderName);
    		if (!folder.exists()) folder.mkdir(); 
    		
        	Validate.validaPathAndName(pathXSD, nameXSD);
            final Document doc = loadXsdDocument(pathXSD.concat(nameXSD));

            //Find the docs root element and use it to find the targetNamespace
            final Element rootElem = doc.getDocumentElement();
            String targetNamespace = null;
            if (rootElem != null && rootElem.getNodeName().equals("xs:schema")) {
                targetNamespace = rootElem.getAttribute("targetNamespace");
            }

            //Parse the file into an XSModel object
            DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            XSImplementation impl = (XSImplementation) registry.getDOMImplementation("XS-Loader");

            XSLoader schemaLoader = impl.createXSLoader(null);
            LSInput input = new DOMInputImpl( );
            input.setByteStream(document2InputStream(doc)); // XSD Schema file in an InpuStream
            XSModel xsModel = schemaLoader.load(input);

            //Define defaults for the XML generation
            XSInstance instance = new XSInstance();
            instance.minimumElementsGenerated = 0;
            instance.maximumElementsGenerated = 0;
            instance.generateDefaultAttributes = true;
            instance.generateOptionalAttributes = true;
            instance.maximumRecursionDepth = 0;
            instance.generateAllChoices = true;
            instance.showContentModel = true;
            instance.generateOptionalElements = true;

            //Build the sample xml doc
            //Replace first param to XMLDoc with a file input stream to write to file
            QName rootElement = new QName(targetNamespace, elementRoot);
            XMLDocument sampleXml = new XMLDocument(new StreamResult(folderName+"/" + nameXSD.substring(0, nameXSD.indexOf(".")) + ".xml"), false, 4, null);
            instance.generate(xsModel, rootElement, sampleXml);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            throw new ConverterException("Ocurrio una TransformerConfigurationException", e);
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
			throw new ConverterException("Ocurrio una TransformerFactoryConfigurationError", e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ConverterException("Ocurrio una ClassNotFoundException", e);
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new ConverterException("Ocurrio una InstantiationException", e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new ConverterException("Ocurrio una IllegalAccessException", e);
		} catch (ClassCastException e) {
			e.printStackTrace();
			throw new ConverterException("Ocurrio una ClassCastException", e);
		} catch (ParameterException e) {
			e.printStackTrace();
			throw new ConverterException("Ocurrio una ParameterException", e);
		}
        
    }
	
    /**
     * Carga el XSD
     * @param inputName
     * @return
     */
    private Document loadXsdDocument(String inputName) throws ConverterException {
        final String filename = inputName;
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setIgnoringComments(true);
        Document doc = null;

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			final File inputFile = new File(filename);
			if(!inputFile.exists()) {
				throw new ConverterException("No se pudo cargar el archivo xsd [" + filename + "]"); 
			}
			doc = builder.parse(inputFile);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			throw new ConverterException("Ocurrio una ParserConfigurationException", e);
		} catch (SAXException e) {
			e.printStackTrace();
			throw new ConverterException("Ocurrio una SAXException", e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ConverterException("Ocurrio una IOException", e);
		}

        return doc;
    }
    
    /**
     * Prepara el InputStream
     * @param doc
     * @return 
     * @throws ConverterException
     */
    private InputStream document2InputStream(Document doc) throws ConverterException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Source xmlSource = new DOMSource(doc);
        Result outputTarget = new StreamResult(outputStream);
		try {
			TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
			throw new ConverterException("Ocurrio una TransformerConfigurationException", e);
		} catch (TransformerException e) {
			e.printStackTrace();
			throw new ConverterException("Ocurrio una TransformerException", e);
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
			throw new ConverterException("Ocurrio una TransformerFactoryConfigurationError", e);
		}

        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		return inputStream;
    }

}
