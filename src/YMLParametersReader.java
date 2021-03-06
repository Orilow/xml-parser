import org.yaml.snakeyaml.Yaml;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class YMLParametersReader {
    public Map<String, String> read(String path) throws IOException {
        InputStream in = Files.newInputStream(Paths.get(path));
        Yaml yaml = new Yaml();
        Map<String, String> map = yaml.load(in);
        Map<String, String> reversedMap = new HashMap<>();
        map.forEach((key, value) -> reversedMap.put(value, key));
        return reversedMap;
    }
}
