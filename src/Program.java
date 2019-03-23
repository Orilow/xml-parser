import org.xml.sax.SAXException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class Program {
    public static void main(String [ ] args) throws ParserConfigurationException, SAXException, IOException {
        if (args.length != 1){
            System.err.println("Wrong usage\nThe first argument must be an xml file");
            System.exit(0);
        }
        else {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            Map<String, String> params = (new YMLReader()).read("src/parametrs.yml");
            SAXHandler handler = new SAXHandler(params);
            try {
                parser.parse(args[0], handler);
            }
            catch (FileNotFoundException exception) {
                System.err.println("File Not Found\nCheck if path is correct");
            }
            catch (Exception exception) {
                System.err.println(exception);
            }
        }
    }
}
