package cn.settile.lson.core;

import cn.settile.lson.exception.JsonParseException;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author lzjyz
 */
public class ParserUtils {
    public static boolean checkExpectToken(Token token, int expectToken) throws JsonParseException {
        return (token.getTokenType().getCode() & expectToken) == 0;
    }

    public static Reader toReader(String json) {
        if (json != null && !"".equals(json.trim())) {
            try {
                ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(json.getBytes());
                return new InputStreamReader(tInputStringStream);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
