import org.xml.sax.SAXException;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class Program {
    public static void main(String [ ] args) throws ParserConfigurationException, SAXException, IOException {
        if (args.length != 1){
//            System.out.println("Wrong usage\nThe first argument must be an xml file");
            System.err.println("Wrong usage\nThe first argument must be an xml file");
            System.exit(0);
        }
        else {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            SAXHandler handler = new SAXHandler();
            parser.parse(args[0], handler);
        }
    }
}
