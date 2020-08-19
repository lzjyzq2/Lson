package cn.settile.lson.core;

import cn.settile.lson.Json;
import cn.settile.lson.JsonArray;
import cn.settile.lson.JsonObject;
import cn.settile.lson.JsonReader;
import cn.settile.lson.exception.*;

import java.io.IOException;
import java.io.Reader;

/**
 * @author lzjyzq2
 * @date 2020-08-07 14:24:30
 */
public class DefaultJsonParser {

    public Json parse(String json) throws JsonParseException, IOException {
        return  parse(ParserUtils.toReader(json));
    }

    public Json parse(Reader reader) throws JsonParseException, IOException {
        if (reader != null) {
            JsonReader jsonReader = new JsonReader(reader);
            Token token = jsonReader.nextToken();
            if (token.getTokenType() == TokenType.BEGIN_OBJECT) {
                return parseObject(jsonReader);
            } else if (token.getTokenType() == TokenType.BEGIN_ARRAY) {
                return parseArray(jsonReader);
            } else {
                throw new JsonParseException("Unexpected Token."+jsonReader.getBufferIndexString());
            }
        }
        return null;
    }
    public JsonObject parseJsonObject(String json) throws JsonParseException, IOException {
        return  parseJsonObject(ParserUtils.toReader(json));
    }
    public JsonObject parseJsonObject(Reader reader) throws JsonParseException, IOException {
        if (reader!=null){
            JsonReader jsonReader = new JsonReader(reader);
            Token token = jsonReader.nextToken();
            if (token.getTokenType() == TokenType.BEGIN_OBJECT) {
                return parseObject(jsonReader);
            } else {
                throw new JsonParseException("Unexpected Token."+jsonReader.getBufferIndexString());
            }
        }
        return null;
    }

    public JsonArray parseJsonArray(String json) throws JsonParseException, IOException {
        return parseJsonArray(ParserUtils.toReader(json));
    }


    public JsonArray parseJsonArray(Reader reader) throws JsonParseException, IOException {
        if (reader!=null){
            JsonReader jsonReader = new JsonReader(reader);
            Token token = jsonReader.nextToken();
            if (token.getTokenType() == TokenType.BEGIN_ARRAY) {
                return parseArray(jsonReader);
            } else {
                throw new JsonParseException("Unexpected Token."+jsonReader.getBufferIndexString());
            }
        }
        return null;
    }

    private JsonObject parseObject(JsonReader jsonReader) throws JsonParseException, IOException {
        if (jsonReader != null) {
            Token token = null;
            JsonObject object = new JsonObject();
            int expectToken = TokenType.STRING.getCode() | TokenType.END_OBJECT.getCode();
            String key = null;
            while ((token = jsonReader.nextToken()).getTokenType() != TokenType.END_DOCUMENT) {
                if(ParserUtils.checkExpectToken(token,expectToken)){
                    throw new JsonParseException("Unexpected Token."+jsonReader.getBufferIndexString());
                }
                TokenType tokenType = token.getTokenType();
                switch (token.getTokenType()) {
                    case BEGIN_OBJECT:
                        // 递归解析 json object
                        object.put(key, parseObject(jsonReader));
                        expectToken = TokenType.SEP_COMMA.getCode() | TokenType.END_OBJECT.getCode();
                        break;
                    case END_OBJECT:
                        return object;
                    case BEGIN_ARRAY:
                        // 解析 json array
                        object.put(key, parseArray(jsonReader));
                        expectToken = TokenType.SEP_COMMA.getCode() | TokenType.END_OBJECT.getCode();
                        break;
                    case NULL:
                        object.put(key, null);
                        expectToken = TokenType.SEP_COMMA.getCode() | TokenType.END_OBJECT.getCode();
                        break;
                    case NUMBER:
                        String tokenValue = token.getValue();
                        if (tokenValue.contains(".") || tokenValue.contains("e") || tokenValue.contains("E")) {
                            object.put(key, Double.valueOf(tokenValue));
                        } else {
                            Long num = Long.valueOf(tokenValue);
                            if (num > Integer.MAX_VALUE || num < Integer.MIN_VALUE) {
                                object.put(key, num);
                            } else {
                                object.put(key, num.intValue());
                            }
                        }
                        expectToken = TokenType.SEP_COMMA.getCode() | TokenType.END_OBJECT.getCode();
                        break;
                    case BOOLEAN:
                        object.put(key, Boolean.valueOf(token.getValue()));
                        expectToken = TokenType.SEP_COMMA.getCode() | TokenType.END_OBJECT.getCode();
                        break;
                    case STRING:
                        /**
                         * 在 JSON 中，字符串既可以作为键，也可作为值。
                         * 作为键时，只期待下一个 Token 类型为 SEP_COLON。
                         * 作为值时，期待下一个 Token 类型为 SEP_COMMA 或 END_OBJECT
                         */
                        Token nextToken = jsonReader.nextToken();
                        if (nextToken.getTokenType() == TokenType.SEP_COLON) {
                            key = token.getValue();
                            expectToken = TokenType.NULL.getCode() | TokenType.NUMBER.getCode() | TokenType.BOOLEAN.getCode() | TokenType.STRING.getCode()
                                    | TokenType.BEGIN_OBJECT.getCode() | TokenType.BEGIN_ARRAY.getCode();
                        } else if (nextToken.getTokenType() == TokenType.SEP_COMMA) {
                            object.put(key, token.getValue());
                            expectToken = TokenType.STRING.getCode();
                        }else if(nextToken.getTokenType() == TokenType.END_OBJECT){
                            ParserUtils.checkExpectToken(token, expectToken);
                            object.put(key, token.getValue());
                            return object;
                        }
                        break;
                    case SEP_COLON:
                        expectToken = TokenType.NULL.getCode() | TokenType.NUMBER.getCode() | TokenType.BOOLEAN.getCode() | TokenType.STRING.getCode()
                                | TokenType.BEGIN_OBJECT.getCode() | TokenType.BEGIN_ARRAY.getCode();
                        break;
                    case SEP_COMMA:
                        expectToken = TokenType.STRING.getCode();
                        break;
                    default:
                        throw new JsonParseException("Unexpected Token."+jsonReader.getBufferIndexString());
                }
            }
            return object;
        } else {
            throw new JsonParseException("JSON Text is null");
        }
    }

    private JsonArray parseArray(JsonReader jsonReader) throws JsonParseException, IOException {
        if (jsonReader != null) {
            Token token = null;
            int expectToken = TokenType.BEGIN_ARRAY.getCode() | TokenType.END_ARRAY.getCode() | TokenType.BEGIN_OBJECT.getCode() | TokenType.NULL.getCode()
                    | TokenType.NUMBER.getCode() | TokenType.BOOLEAN.getCode() | TokenType.STRING.getCode();
            JsonArray jsonArray = new JsonArray();
            while ((token = jsonReader.nextToken()).getTokenType() != TokenType.END_DOCUMENT) {
                if(ParserUtils.checkExpectToken(token,expectToken)){
                    throw new JsonParseException("Unexpected Token."+jsonReader.getBufferIndexString());
                }
                TokenType tokenType = token.getTokenType();
                switch (tokenType) {
                    case BEGIN_OBJECT:
                        jsonArray.add(parseObject(jsonReader));
                        expectToken = TokenType.SEP_COMMA.getCode() | TokenType.END_ARRAY.getCode();
                        break;
                    case BEGIN_ARRAY:
                        jsonArray.add(parseArray(jsonReader));
                        expectToken = TokenType.SEP_COMMA.getCode() | TokenType.END_ARRAY.getCode();
                        break;
                    case END_ARRAY:
                        return jsonArray;
                    case NULL:
                        jsonArray.add(null);
                        expectToken = TokenType.SEP_COMMA.getCode() | TokenType.END_ARRAY.getCode();
                        break;
                    case NUMBER:
                        if (token.getValue().contains(".") || token.getValue().contains("e") || token.getValue().contains("E")) {
                            jsonArray.add(Double.valueOf(token.getValue()));
                        } else {
                            Long num = Long.valueOf(token.getValue());
                            if (num > Integer.MAX_VALUE || num < Integer.MIN_VALUE) {
                                jsonArray.add(num);
                            } else {
                                jsonArray.add(num.intValue());
                            }
                        }
                        expectToken = TokenType.SEP_COMMA.getCode() | TokenType.END_ARRAY.getCode();
                        break;
                    case BOOLEAN:
                        jsonArray.add(Boolean.valueOf(token.getValue()));
                        expectToken = TokenType.SEP_COMMA.getCode() | TokenType.END_ARRAY.getCode();
                        break;
                    case STRING:
                        jsonArray.add(token.getValue());
                        expectToken = TokenType.SEP_COMMA.getCode() | TokenType.END_ARRAY.getCode();
                        break;
                    case SEP_COMMA:
                        expectToken = TokenType.STRING.getCode() | TokenType.NULL.getCode() | TokenType.NUMBER.getCode() | TokenType.BOOLEAN.getCode()
                                | TokenType.BEGIN_ARRAY.getCode() | TokenType.BEGIN_OBJECT.getCode();
                        break;
                    default:
                        throw new JsonParseException("Unexpected Token."+jsonReader.getBufferIndexString());
                }
            }
            return jsonArray;
        } else {
            throw new JsonParseException("JSON Text is null");
        }
    }
}
