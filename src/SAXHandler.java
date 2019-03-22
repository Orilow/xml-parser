import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Map;
import java.util.Stack;

public class SAXHandler extends DefaultHandler {
    private Stack<String> nodes = new Stack<String>();
    private String currentElement = null;
    private Map<String, String params = null;

    public SAXHandler(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        nodes.push(qName);
        currentElement = qName;
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        currentElement = nodes.pop();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

    }
}
