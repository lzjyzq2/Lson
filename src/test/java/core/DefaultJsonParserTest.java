package core;

import cn.settile.lson.JsonArray;
import cn.settile.lson.JsonObject;
import cn.settile.lson.core.DefaultJsonParser;
import cn.settile.lson.exception.JsonParseException;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DefaultJsonParserTest {

    @Test
    public void Test() throws IOException, JsonParseException {

        String jsonArrayText = "[{\n" +
                "        \"name\":\"Google\",\n" +
                "        \"url\":\"http://www.google.com\"\n" +
                "    },\n" +
                "    {\n" +
                "       \"name\":\"Baidu\",\n" +
                "       \"url\":\"http://www.baidu.com\"\n" +
                "   },\n" +
                "   {\n" +
                "       \"name\":\"SoSo\",\n" +
                "       \"url\":\"http://www.SoSo.com\"\n" +
                "   }]";

        String jsonObjectText = "{\n" +
                "    \"name\": \"中国\",\n" +
                "    \"province\": [{\n" +
                "        \"name\": \"黑龙江\",\n" +
                "        \"cities\": {\n" +
                "            \"city\": [\"哈尔滨\", \"大庆\"]\n" +
                "        }\n" +
                "    }, {\n" +
                "        \"name\": \"广东\",\n" +
                "        \"cities\": {\n" +
                "            \"city\": [\"广州\", \"深圳\", \"珠海\"]\n" +
                "        }\n" +
                "    }, {\n" +
                "        \"name\": \"台湾\",\n" +
                "        \"cities\": {\n" +
                "            \"city\": [\"台北\", \"高雄\"]\n" +
                "        }\n" +
                "    }, {\n" +
                "        \"name\": \"新疆\",\n" +
                "        \"cities\": {\n" +
                "        \"city\": [\"乌鲁木齐\"]\n" +
                "        }\n" +
                "    }]\n" +
                "}";
        System.out.println("-- Read in JSON String is JsonObject  ----------------------------------------------");
        DefaultJsonParser defaultJsonParser = new DefaultJsonParser();
        JsonObject jsonObject = (JsonObject) defaultJsonParser.parse(jsonObjectText);
        System.out.println(jsonObject);
        System.out.println("-- Read in JSON File is JsonObject ----------------------------------------------");
        defaultJsonParser = new DefaultJsonParser();
        InputStream in = this.getClass().getResourceAsStream("/JsonReaderTest.json");
        InputStreamReader reader = new InputStreamReader(in);
        jsonObject = (JsonObject) defaultJsonParser.parse(reader);
        System.out.println(jsonObject);
        reader.close();

        System.out.println("-- Read in JSON String is JsonArray ----------------------------------------------");
        JsonArray jsonArray = defaultJsonParser.parseJsonArray(jsonArrayText);
        System.out.println(jsonArray);

    }
}
