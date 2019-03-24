import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import java.io.IOException;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Map;


public class XMLParametersReader {
    private SAXParser parametersParser;
    private XMLParametersHandler parametersHandler;
    private Map<String, String> parameters;


    public XMLParametersReader() throws ParserConfigurationException, SAXException {
        parametersParser = SAXParserFactory.newInstance().newSAXParser();
        parametersHandler = new XMLParametersHandler();
        parameters = new HashMap<>();
    }

    public Map<String, String> read(String paramsPath) throws IOException, SAXException {
        parametersParser.parse(paramsPath, parametersHandler);
        return parameters;
    }


    private class XMLParametersHandler extends DefaultHandler{
        private String currentElement = null;

        @Override
        public void startElement(String namespaceURI,
                                 String localName,
                                 String qName,
                                 Attributes atts)
                throws SAXException {
            if (!qName.equals("document")){
                currentElement = qName;
            }
        }

        @Override
        public void characters(char[] ch,
                               int start,
                               int length)
                throws SAXException {
            if (currentElement != null) {
                parameters.put(new String(Arrays.copyOfRange(ch, start, start + length)),
                        currentElement);
                currentElement = null;
            }
        }
    }
}
