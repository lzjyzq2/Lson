package cn.settile.lson;

import cn.settile.lson.core.DefaultJsonParser;
import cn.settile.lson.exception.JsonParseException;

import java.io.IOException;
import java.io.Reader;

/**
 * @author lzjyzq2
 * @date 2020-08-07 14:23:47
 */
public class Json {

    private static DefaultJsonParser defaultJsonParser = new DefaultJsonParser();

    public static Json parseJson(String json) throws Exception {
        return defaultJsonParser.parse(json);
    }

    public static Json parseJson(Reader reader) throws Exception {
        return defaultJsonParser.parse(reader);
    }

    public static JsonObject parseJsonObject(String json) throws JsonParseException, IOException {
        return defaultJsonParser.parseJsonObject(json);
    }

    public static JsonObject parseJsonObject(Reader reader) throws JsonParseException, IOException {
        return defaultJsonParser.parseJsonObject(reader);
    }

    public static JsonArray parseJsonArray(String json) throws JsonParseException, IOException {
        return defaultJsonParser.parseJsonArray(json);
    }

    public static JsonArray parseJsonArray(Reader reader) throws JsonParseException, IOException {
        return defaultJsonParser.parseJsonArray(reader);
    }


}
