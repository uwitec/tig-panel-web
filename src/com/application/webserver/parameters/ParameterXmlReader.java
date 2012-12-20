package com.application.webserver.parameters;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ParameterXmlReader {
	
	private static EntityResolver entityResolver = new ParameterXmlEntityResolver();

	public static Document readParameterFromXml(String xmlLocation) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Reader reader = new InputStreamReader(loader.getResourceAsStream(xmlLocation));

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		factory.setNamespaceAware(false);
		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(false);
		factory.setCoalescing(false);
		factory.setExpandEntityReferences(false);

		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		builder.setEntityResolver(entityResolver);
		builder.setErrorHandler(new ErrorHandler() {
			public void error(SAXParseException exception) throws SAXException {
				throw exception;
			}

			public void fatalError(SAXParseException exception)
					throws SAXException {
				throw exception;
			}

			public void warning(SAXParseException exception)
					throws SAXException {
			}
		});

		Document document = null;
		try {
			document = builder.parse(new InputSource(reader));
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
	}
}
