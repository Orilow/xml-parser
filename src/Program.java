import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileNotFoundException;
import javax.xml.parsers.SAXParser;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.util.Map;


public class Program {
    public static void main(String [ ] args) throws ParserConfigurationException, SAXException, IOException {
        if (args.length != 1){
            System.err.println("Wrong usage\nThe first argument must be an xml file");
            System.exit(0);
        }
        else {
            Map<String, String> params = readParams("src/parametrs.xml");

            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
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

    private static Map<String, String> readParams(String paramsPath) throws IOException, ParserConfigurationException, SAXException {
        if (paramsPath.endsWith(".yml"))
            return (new YMLParametersReader()).read(paramsPath);
        else if (paramsPath.endsWith(".xml"))
            return (new XMLParametersReader()).read(paramsPath);
        else {
            System.err.println("Wrong parameters format");
            System.exit(0);
            return null;
        }
    }
}
