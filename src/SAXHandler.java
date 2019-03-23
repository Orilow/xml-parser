import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import static java.util.Collections.reverse;

public class SAXHandler extends DefaultHandler {
    private Stack<String> nodes = new Stack<String>();
    private String currentElement = null;
    private String currentPath = null;
    private Map<String, String> params = null;

    public SAXHandler(Map<String, String> params) {
        this.params = params;
    }

    private String getCurrentPath(String qName) {
        return nodes.stream().collect(Collectors.joining("/"));
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        nodes.push(qName);
        currentElement = qName;
        params.forEach((String key, String value) -> {
            if (key.endsWith(qName)) {
                if (!key.contains("**")) {
                    if (getCurrentPath(qName).endsWith(key))
                        currentPath = key;
                }
                else {
                    List<String> splittedPath = Arrays.asList(key.split("/\\*\\*/|\\/"));
                    Stack<String> splittedPathStack = new Stack<>();
                    splittedPathStack.addAll(splittedPath);
                    reverse(splittedPathStack);
                    for (String node : nodes) {
                        if (splittedPathStack.peek().equals(node))
                            splittedPathStack.pop();
                    }
                    if (splittedPathStack.isEmpty())
                        currentPath = key;
                }
            }
        });
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        currentElement = nodes.pop();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentPath != null) {
            String text = new String(Arrays.copyOfRange(ch, start, start + length));
            System.out.println(String.format("%s = %s", params.get(currentPath), text));
            currentPath = null;
        }
    }
}
