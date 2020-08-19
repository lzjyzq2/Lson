package core;
import cn.settile.lson.JsonReader;
import cn.settile.lson.core.Token;
import cn.settile.lson.core.TokenType;
import cn.settile.lson.exception.JsonParseException;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonReaderTest {
    @Test
    public void Test() throws IOException, JsonParseException {

        System.out.println("-- Read in JSON String ----------------------------------------------");
        String json = "[\n" +
                "         {\n" +
                "             \"name\": \"“给我唱首歌”\\n“给我讲个故事”\"\n" +
                "         },\n" +
                "\n" +
                "         {\n" +
                "             \"name\": \"“我想订机票”\\n“给我说个新闻”\"\n" +
                "         },\n" +
                "\n" +
                "         {\n" +
                "             \"name\": \"“给我讲个笑话”\\n“来一首李白的静夜思”\"\n" +
                "         },\n" +
                "\n" +
                "         {\n" +
                "             \"name\": \"“看一下去上海的航班”\\n“百科搜索张学\u53cb”\"\n" +
                "         },\n" +
                "\n" +
                "         {\n" +
                "             \"name\": \"“如果嫌我太烦”\\n可以对我说“休息去吧”\"\n" +
                "         }\n" +
                "]";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(json.getBytes());
        InputStreamReader reader = new InputStreamReader(inputStream);
        JsonReader jsonReader = new JsonReader(reader);
        Token token = null;
        while ((token = jsonReader.nextToken()).getTokenType()!= TokenType.END_DOCUMENT){
            System.out.println("token:"+token.getValue());
        }
        jsonReader.close();
        reader.close();
        inputStream.close();

        System.out.println("-- Read in JSON File ----------------------------------------------");
        InputStream in = this.getClass().getResourceAsStream("/JsonReaderTest.json");
        reader = new InputStreamReader(in);
        jsonReader = new JsonReader(reader);
        while ((token = jsonReader.nextToken()).getTokenType()!= TokenType.END_DOCUMENT){
            System.out.println("token:"+token.getValue());
        }
        jsonReader.close();
        reader.close();
    }
}
