import static java.util.Collections.reverse;
import org.xml.sax.helpers.DefaultHandler;
import java.util.stream.Collectors;
import org.xml.sax.Attributes;
import java.util.Arrays;
import java.util.Stack;
import java.util.List;
import java.util.Map;


public class SAXHandler extends DefaultHandler {
    private Stack<String> nodes = new Stack<>();
    private String matchedPath = null;
    private Map<String, String> params;

    SAXHandler(Map<String, String> params) { this.params = params; }

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
                matchedPath = patternPath;
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
                matchedPath = patternPath;
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) { nodes.pop(); }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (matchedPath != null) {
            String text = new String(Arrays.copyOfRange(ch, start, start + length));
            System.out.println(String.format("%s = %s", params.get(matchedPath), text));
            matchedPath = null;
        }
    }
}
