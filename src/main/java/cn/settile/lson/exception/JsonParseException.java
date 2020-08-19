package cn.settile.lson.exception;

/**
 * @author lzjyzq2
 * @date 2020-08-07 22:16:23
 */
public class JsonParseException extends Exception {
    public JsonParseException() {
        super();
    }

    public JsonParseException(String message) {
        super(message);
    }

    public JsonParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
