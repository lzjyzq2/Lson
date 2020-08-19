package cn.settile.lson.core;

import cn.settile.lson.JsonReader;
import cn.settile.lson.exception.*;
import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * @author lzjyzq2
 * @date 2020-08-11 21:12:01
 */
public class ObjectParser {

    DateFormatter dateFormatter;
    JsonReader jsonReader;

    public <T> T toObject(String json, Class<T> tClass) throws InstantiationException, IllegalAccessException, JsonParseException, IOException, InvocationTargetException, NoSuchFieldException, NoSuchMethodException {
        return toObject(ParserUtils.toReader(json), tClass);
    }

    public <T> T toObject(Reader reader, Class<T> tClass) throws IOException, JsonParseException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchFieldException, NoSuchMethodException {
        if (reader != null) {
            jsonReader = new JsonReader(reader);
        }
        int expectToken = TokenType.BEGIN_OBJECT.getCode();
        assert jsonReader != null;
        Token token = jsonReader.nextToken();
        T t = null;
        if (tClass.isArray()) {
            if (ParserUtils.checkExpectToken(token, expectToken)) {
                throw new JsonParseException("Unexpected Token." + jsonReader.getBufferIndexString());
            }
            t = parseArray(jsonReader, tClass);
        } else if (tClass.isAssignableFrom(Map.class)) {

        } else if (tClass.isAssignableFrom(Collection.class)) {

        } else {
            if (ParserUtils.checkExpectToken(token, expectToken)) {
                throw new JsonParseException("Unexpected Token." + jsonReader.getBufferIndexString());
            }
            t = parseObject(tClass);
        }
        return t;
    }

    private <T> T parseObject(Class<T> tClass) throws JsonParseException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, NoSuchMethodException {
        PropertyMatcher matcher = new PropertyMatcher(tClass);
        if (jsonReader != null) {
            Token token = null;
            T instance = null;
            int expectToken = TokenType.STRING.getCode() | TokenType.END_OBJECT.getCode();
            instance = (T) createNewInstance(tClass);
            String key = "";
            Field field = null;
            while ((token = jsonReader.nextToken()).getTokenType() != TokenType.END_DOCUMENT) {
                TokenType tokenType = token.getTokenType();

                switch (tokenType) {
                    case BEGIN_OBJECT:
                        System.out.println(field.getGenericType());
                        setValue(instance, tClass, key, field.getType(), convertObject(instance,tClass,key,field));
                        break;
                    case END_OBJECT:
                        return instance;
                    case BEGIN_ARRAY:
                        //return parseArray(jsonReader,tClass);
                        break;
                    case STRING:
                        Token nextToken = jsonReader.nextToken();
                        if (nextToken.getTokenType() == TokenType.SEP_COLON) {
                            key = token.getValue();
                            expectToken = TokenType.NULL.getCode() | TokenType.NUMBER.getCode() | TokenType.BOOLEAN.getCode() | TokenType.STRING.getCode()
                                    | TokenType.BEGIN_OBJECT.getCode() | TokenType.BEGIN_ARRAY.getCode();
                            field = matcher.match(key);
                        } else {
                            if (field.getType() == char.class) {
                                if (token.getValue().length() != 1) {
                                    throw new JsonParseException("Unexpect json value");
                                }
                                setValue(instance, tClass, key, char.class, token.getValue().charAt(0));
                            } else if(field.getType()==String.class){
                                setValue(instance, tClass, key, String.class, token.getValue());
                            }else if(field.getType()==Date.class){

                            }
                            if (nextToken.getTokenType() == TokenType.SEP_COMMA) {
                                expectToken = TokenType.STRING.getCode();
                            } else if (nextToken.getTokenType() == TokenType.END_OBJECT) {
                                ParserUtils.checkExpectToken(token, expectToken);
                                return instance;
                            }
                        }
                        break;
                    case BOOLEAN:
                        assert field != null;
                        if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                            setValue(instance, tClass, key, field.getType(), Boolean.valueOf(token.getValue()));
                        }
                        expectToken = TokenType.SEP_COMMA.getCode() | TokenType.END_OBJECT.getCode();
                        break;
                    case SEP_COLON:
                        expectToken = TokenType.NULL.getCode() | TokenType.NUMBER.getCode() | TokenType.BOOLEAN.getCode() | TokenType.STRING.getCode()
                                | TokenType.BEGIN_OBJECT.getCode() | TokenType.BEGIN_ARRAY.getCode();
                        break;
                    case SEP_COMMA:
                        expectToken = TokenType.STRING.getCode();
                        break;
                    case NULL:
                        if (!field.getType().isPrimitive()) {
                            setValue(instance, tClass, key, field.getType(), null);
                        }
                        return null;
                    case NUMBER:
                        assert field != null;
                        setValue(instance, tClass, key, field.getType(), convertNumber(field.getType(),token.getValue()));
                        break;
                    default:
                        throw new JsonParseException("Unexpected Token." + jsonReader.getBufferIndexString());
                }
            }
            return instance;
        } else {
            throw new JsonParseException("JSON Text is null");
        }
    }

    public <T extends Map<String,Object>> T parseMap(JsonReader jsonReader, Class<T> tClass) throws JsonParseException, IOException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, InstantiationException {
        PropertyMatcher matcher = new PropertyMatcher(tClass);
        if (jsonReader != null) {
            Token token = null;
            T instance = null;
            int expectToken = TokenType.STRING.getCode() | TokenType.END_OBJECT.getCode();
            instance = (T) createNewInstance(tClass);
            String key = "";
            Field field = null;
            while ((token = jsonReader.nextToken()).getTokenType() != TokenType.END_DOCUMENT) {
                TokenType tokenType = token.getTokenType();
                switch (tokenType) {
                    case BEGIN_OBJECT:
                        setValue(instance, tClass, key, field.getType(), convertObject(instance,tClass,key,field));
                        break;
                    case END_OBJECT:
                        return instance;
                    case BEGIN_ARRAY:
                        //return parseArray(jsonReader,tClass);
                        break;
                    case STRING:
                        Token nextToken = jsonReader.nextToken();
                        if (nextToken.getTokenType() == TokenType.SEP_COLON) {
                            key = token.getValue();
                            expectToken = TokenType.NULL.getCode() | TokenType.NUMBER.getCode() | TokenType.BOOLEAN.getCode() | TokenType.STRING.getCode()
                                    | TokenType.BEGIN_OBJECT.getCode() | TokenType.BEGIN_ARRAY.getCode();
                        } else if (nextToken.getTokenType() == TokenType.SEP_COMMA) {
                            instance.put(key, token.getValue());
                            expectToken = TokenType.STRING.getCode();
                        }else if(nextToken.getTokenType() == TokenType.END_OBJECT){
                            ParserUtils.checkExpectToken(token, expectToken);
                            instance.put(key, token.getValue());
                            return instance;
                        }
                        break;
                    case BOOLEAN:
                        assert field != null;
                        if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                            instance.put(key,Boolean.valueOf(token.getValue()));
                        }else {
                            throw new JsonParseException();
                        }
                        expectToken = TokenType.SEP_COMMA.getCode() | TokenType.END_OBJECT.getCode();
                        break;
                    case SEP_COLON:
                        expectToken = TokenType.NULL.getCode() | TokenType.NUMBER.getCode() | TokenType.BOOLEAN.getCode() | TokenType.STRING.getCode()
                                | TokenType.BEGIN_OBJECT.getCode() | TokenType.BEGIN_ARRAY.getCode();
                        break;
                    case SEP_COMMA:
                        expectToken = TokenType.STRING.getCode();
                        break;
                    case NULL:
                        if (!field.getType().isPrimitive()) {
                            instance.put(key,null);
                        }
                        return null;
                    case NUMBER:
                        assert field != null;
                        instance.put(key,convertNumber(field.getType(),token.getValue()));
                        break;
                    default:
                        throw new JsonParseException("Unexpected Token." + jsonReader.getBufferIndexString());
                }
            }
            return instance;
        } else {
            throw new JsonParseException("JSON Text is null");
        }
    }

    private <T> T parseArray(JsonReader jsonReader, Class<T> tClass) throws JsonParseException, IllegalAccessException, InstantiationException, IOException {
        List<T> tList = new ArrayList<>();
        if (jsonReader != null) {
            Token token = null;
            T instance = null;
            int expectToken = TokenType.BEGIN_ARRAY.getCode() | TokenType.END_ARRAY.getCode() | TokenType.BEGIN_OBJECT.getCode() | TokenType.NULL.getCode()
                    | TokenType.NUMBER.getCode() | TokenType.BOOLEAN.getCode() | TokenType.STRING.getCode();
            try {
                instance = tClass.getDeclaredConstructor().newInstance();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e1) {
                throw new JsonParseException("No zero args constructor", e1);
            }
            return null;
        } else {
            throw new JsonParseException("JSON Text is null");
        }
    }

    private Object createNewInstance(Class<?> tClass) throws JsonParseException{
        try {
            if (tClass.isMemberClass() && !Modifier.isStatic(tClass.getModifiers())) {
                throw new JsonParseException(tClass.toString() + "no static inner class");
            }else {
                if(tClass.isInterface()){

                }
                if(tClass.isAnonymousClass()){

                }
                if(Modifier.isAbstract(tClass.getModifiers())){
                    if (tClass == Map.class){
                        ParameterizedType type = (ParameterizedType) tClass.getGenericSuperclass();
                        Class c = (Class) type.getActualTypeArguments()[0];
                        return new LinkedHashMap<>();
                    }
                }
            }
            Constructor constructor = tClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new JsonParseException("No zero args constructor", e);
        }
        return null;
    }

    private Object convertObject(Object instance, Class<?> tClass, String key, Field field) throws JsonParseException, IllegalAccessException, IOException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        if(field != null&&!field.getType().isPrimitive()){
            if(field.getType().isAssignableFrom(Map.class)){
                 return parseMap(jsonReader,(Class)field.getType());
            }else if (field.getType().isAssignableFrom(Collection.class)){

            }else if(field.getType().isArray()){

            }else{
                return parseObject((Class<? extends Object>) field.getGenericType());
            }
        }else{
            throw new JsonParseException("Unexpected Token.");
        }
        return null;
    }
    private Object convertNumber(Class<?> filedType, String value){
        if (filedType == int.class || filedType == Integer.class) {
            return Integer.valueOf(value);
        } else if (filedType == short.class || filedType == Short.class) {
            return Short.valueOf(value);
        } else if (filedType == byte.class || filedType == Byte.class) {
            return Byte.valueOf(value);
        } else if (filedType == long.class || filedType == Long.class) {
            return Long.valueOf(value);
        } else if (filedType == float.class || filedType == Float.class) {
            return Float.valueOf(value);
        } else if (filedType == double.class || filedType == Double.class) {
            return Double.valueOf(value);
        } else if (filedType == BigInteger.class) {
            return new BigInteger(value);
        } else if (filedType == BigDecimal.class) {
            return BigDecimal.valueOf(Double.parseDouble(value));
        }else {
            return 0;
        }
    }


    /**
     * 根据属性，拿到set方法，并把值set到对象中
     *
     * @param obj       对象
     * @param clazz     对象的class
     * @param filedName 需要设置值得属性
     * @param typeClass
     * @param value
     */
    private static void setValue(Object obj, Class<?> clazz, String filedName, Class<?> typeClass, Object value) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        boolean flag = true;
        for (Method method : clazz.getMethods()) {
            String methodName = "set" + filedName.toLowerCase();
            if (method.getName().toLowerCase().equals(methodName) && method.getParameterCount() == 1 && method.getParameterTypes()[0] == typeClass) {
                method.invoke(obj, value);
                flag = false;
                break;
            }
        }
        if (flag) {
            Field field = obj.getClass().getDeclaredField(filedName);
            field.setAccessible(true);
            field.set(obj, value);
        }
    }

    /**
     * 根据属性，获取get方法
     *
     * @param ob        对象
     * @param filedName 属性名
     * @param filedType 属性类型
     * @return 属性值
     * @throws Exception
     */
    public static Object getValue(Object ob, String filedName, Class<?> filedType) throws Exception {
        String methodName = "get" + filedName.toLowerCase();
        if (filedType == boolean.class || filedType == Boolean.class) {
            methodName = "is" + filedName.toLowerCase();
        }
        for (Method method : ob.getClass().getMethods()) {
            if (method.getName().toLowerCase().equals(methodName) && method.getParameterCount() == 0 && method.getReturnType() == filedType) {
                return method.invoke(ob);
            }
        }
        Field field = ob.getClass().getDeclaredField(filedName);
        field.setAccessible(true);
        return field.get(ob);
    }
}
