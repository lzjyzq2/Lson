package cn.settile.lson.core;

import cn.settile.lson.JsonArray;
import cn.settile.lson.JsonObject;
import cn.settile.lson.exception.JsonSerializeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lzjyzq2
 * @date 2020-08-10 11:13:01
 */
public class DefaultSerializer {

    private static int depth = 0;
    private static FormatterConfig defaultFormatterConfig = new FormatterConfig();

    private static FormatterConfig defaultBeautifyConfig = new FormatterConfig.Builder()
            .setLeftObjectSepBreak(true)
            .setRightObjectSepBreak(false)
            .setLeftArraySepBreak(true)
            .setRightArraySepBreak(true)
            .setCommaBreak(false)
            .setIndents(2)
            .build();

    public static String toJsonString(JsonObject jsonObject) throws JsonSerializeException {
        return toJsonString(jsonObject,defaultFormatterConfig);
    }

    public static String toJsonString(JsonArray jsonArray) throws JsonSerializeException {
        return toJsonString(jsonArray,defaultFormatterConfig);
    }

    public static String toBeautifyJson(JsonObject jsonObject) throws JsonSerializeException {
        return toJsonString(jsonObject,defaultBeautifyConfig);
    }
    public static String toBeautifyJson(JsonArray jsonArray) throws JsonSerializeException {
        return toJsonString(jsonArray,defaultBeautifyConfig);
    }

    public static String toJsonString(JsonObject jsonObject,FormatterConfig fc) throws JsonSerializeException {
        List<Map.Entry<String,Object>> kv =  new ArrayList<>(jsonObject.entrySet());
        StringBuffer sb = new StringBuffer();
        sb.append(getIndentString(fc));
        sb.append("{");
        if(depth>=Integer.MAX_VALUE){
            throw new JsonSerializeException("Call Depth exceeds the Integer.MAX_VALUE.");
        }
        depth++;
        int size = kv.size();
        for (int i = 0; i < size; i++) {
            Map.Entry<String, Object> keyValue = kv.get(i);

            String key = keyValue.getKey();
            Object value = keyValue.getValue();

            if(fc.isLeftObjectSepBreak()){
                sb.append("\n");
            }
            sb.append(getIndentString(fc));
            sb.append("\"");
            sb.append(key);
            sb.append("\"");
            sb.append(": ");

            if (value instanceof JsonObject) {
                if(fc.isLeftObjectSepBreak()){
                    sb.append("\n");
                }
                sb.append(toJsonString((JsonObject) value,fc));
            } else if (value instanceof JsonArray){
                if(fc.isLeftArraySepBreak()){
                    sb.append("\n");
                }
                sb.append(toJsonString((JsonArray) value,fc));
            } else if (value instanceof String) {
                sb.append("\"");
                sb.append(value);
                sb.append("\"");
            } else {
                sb.append(value);
            }

            if (i < size - 1) {
                sb.append(",");
                if(fc.isCommaBreak()){
                    sb.append("\n");
                }
            }
        }

        depth--;
        if(fc.isRightArraySepBreak()){
            sb.append("\n");
        }
        sb.append(getIndentString(fc));
        sb.append("}");

        return sb.toString();
    }

    public static String toJsonString(JsonArray jsonArray,FormatterConfig fc) throws JsonSerializeException {
        StringBuilder sb = new StringBuilder();
        sb.append(getIndentString(fc));
        sb.append("[");
        if(depth>=Integer.MAX_VALUE){
            throw new JsonSerializeException("Call Depth exceeds the Integer.MAX_VALUE.");
        }
        depth++;
        int size = jsonArray.size();
        for (int i = 0; i < size; i++) {
            if(fc.isLeftArraySepBreak()){
                sb.append("\n");
            }
            Object ele = jsonArray.get(i);
            if (ele instanceof JsonObject) {
                sb.append(toJsonString((JsonObject) ele,fc));
            } else if (ele instanceof JsonArray) {
                sb.append(toJsonString((JsonArray) ele,fc));
            } else if (ele instanceof String) {
                sb.append(getIndentString(fc));
                sb.append("\"");
                sb.append(ele);
                sb.append("\"");
            } else {
                sb.append(getIndentString(fc));
                sb.append(ele);
            }
            if (i < size - 1) {
                sb.append(",");
                if(fc.isCommaBreak()) {
                    sb.append("\n");
                }
            }
        }
        depth--;
        if(fc.isRightArraySepBreak()){
            sb.append("\n");
        }
        sb.append(getIndentString(fc));
        sb.append("]");
        return sb.toString();
    }

    private static String getIndentString(FormatterConfig fc){
        return String.valueOf(fc.getIndentChar()).repeat(Math.max(0, depth * fc.getIndents()));
    }
}
