package cn.settile.lson.core;

import cn.settile.lson.annotation.JsonIgnore;
import cn.settile.lson.annotation.JsonProperty;
import cn.settile.lson.annotation.JsonPropertyIgnore;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lzjyzq2
 * @date 2020-08-10 21:02:34
 */
public class PropertyMatcher {
    private Class targetClz;
    private Object target;
    private Map<String,String> fields;
    private static Class<?>[] classes = new Class[]{
            boolean.class,
            byte.class,
            short.class,
            int.class,
            long.class,
            float.class,
            double.class,

            Boolean.class,
            Byte.class,
            Short.class,
            Integer.class,
            Long.class,
            Float.class,
            Double.class,

            BigInteger.class,
            BigDecimal.class,
            String.class
    };

    public PropertyMatcher(Class targetClz) {
        this.targetClz = targetClz;
        initFields();
    }


    public PropertyMatcher(Object target) {
        this.target = target;
        this.targetClz = target.getClass();
        initFields();
    }

    /**
     * 查找是否存在匹配的属性
     *
     * @param key
     * @return 若不存在返回 {@code null},若存在返回期望值 Type
     */
    public Field match(String key){
        if(fields.containsKey(key)){
            try {
                return targetClz.getDeclaredField(fields.get(key));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void initFields() {
        Field[] declaredFields = targetClz.getDeclaredFields();
        fields = new HashMap<>();
        if (targetClz.isAnnotationPresent(JsonIgnore.class)) {
            for (Field field : declaredFields) {
                if (field.isAnnotationPresent(JsonProperty.class)) {
                    String key = field.getAnnotation(JsonProperty.class).key();
                    if(key.trim().isBlank()){
                        key = field.getName();
                    }
                    fields.put(key,field.getName());
                }
            }
        } else {
            for (Field field : declaredFields) {
                if (field.isAnnotationPresent(JsonPropertyIgnore.class)) {
                    continue;
                }
                if (field.isAnnotationPresent(JsonProperty.class)) {
                    String key = field.getAnnotation(JsonProperty.class).key();
                    if(key.trim().isBlank()){
                        key = field.getName();
                    }
                    fields.put(key,field.getName());
                }else {
                    fields.put(field.getName(),field.getName());
                }
            }
        }
    }

    @Override
    public String toString() {
        return "PropertyMatcher{" +
                "targetClz=" + targetClz +
                ", fields=" + fields +
                ", target=" + target +
                '}';
    }
}
