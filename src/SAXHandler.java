import static java.util.Collections.reverse;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;
import java.util.stream.Collectors;
import org.xml.sax.Attributes;


public class SAXHandler extends DefaultHandler {
    private Stack<String> nodes = new Stack<>();
    private LinkedList<String> matchedPaths = new LinkedList<>();
    private Map<String, String> params;

    SAXHandler(Map<String, String> params) {
        this.params = params;
    }

    private String getCurrentPath() { return nodes.stream().collect(Collectors.joining("/")); }

    @Override
    public void startElement(String namespaceURI,
                             String localName,
                             String qName,
                             Attributes atts) {
        nodes.push(qName);
        params.forEach((String patternPath, String varName) -> {
            if (patternPath.endsWith(qName))
                TryToMatchPattern(patternPath);
        });
    }

    private void TryToMatchPattern(String patternPath) {
        if (!patternPath.contains("**")) {
            if (getCurrentPath().endsWith(patternPath))
                matchedPaths.add(patternPath);
        }
        else {
            List<String> splittedPatternPath = Arrays.asList(patternPath.split("/\\*\\*/|\\/"));
            Stack<String> splittedPatternPathStack = new Stack<>();
            splittedPatternPathStack.addAll(splittedPatternPath);
            reverse(splittedPatternPathStack);
            for (String node : nodes) {
                if (splittedPatternPathStack.peek().equals(node))
                    splittedPatternPathStack.pop();
            }
            if (splittedPatternPathStack.isEmpty())
                matchedPaths.add(patternPath);
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) { nodes.pop(); }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (matchedPaths.size() != 0) {
            String text = new String(Arrays.copyOfRange(ch, start, start + length));
            if (text.trim().length() > 0) {
                matchedPaths.forEach(path -> {
                    System.out.println(String.format("%s = %s", params.get(path), text));
                });
                matchedPaths.clear();
            }
        }
    }
}
