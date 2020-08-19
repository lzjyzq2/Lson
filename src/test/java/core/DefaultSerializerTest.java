package core;

import cn.luern0313.lson.core.*;
import cn.luern0313.lson.exception.JsonParseException;
import cn.luern0313.lson.exception.JsonSerializeException;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DefaultSerializerTest {

    @Test
    public void Test() throws JsonParseException, IOException, JsonSerializeException {
        System.out.println("-- Read in JSON File ----------------------------------------------");
        DefaultJsonParser defaultJsonParser = new DefaultJsonParser();
        InputStream in = this.getClass().getResourceAsStream("/JsonReaderTest.json");
        InputStreamReader reader = new InputStreamReader(in);
        JsonObject jsonObject = (JsonObject) defaultJsonParser.parse(reader);
        System.out.println(jsonObject);
        System.out.println(DefaultSerializer.toBeautifyJson(jsonObject));
        reader.close();
    }
}
