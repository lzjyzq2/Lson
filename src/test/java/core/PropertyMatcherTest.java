package core;

import cn.settile.lson.annotation.JsonIgnore;
import cn.settile.lson.annotation.JsonProperty;
import cn.settile.lson.annotation.JsonPropertyIgnore;
import cn.settile.lson.core.PropertyMatcher;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PropertyMatcherTest {

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

    @Test
    public void Test() {
        PropertyMatcher propertyMatcher1 = new PropertyMatcher(new Data());
        System.out.println(propertyMatcher1);
        Field integers = propertyMatcher1.match("integers");
        System.out.println(integers.getGenericType());
        System.out.println(((ParameterizedType) integers.getGenericType()).getActualTypeArguments()[0]);


        PropertyMatcher propertyMatcher2 = new PropertyMatcher(new Row());
        System.out.println(propertyMatcher2);

        PropertyMatcher propertyMatcher3 = new PropertyMatcher(new Col());
        System.out.println(propertyMatcher3);
    }
}

class Data {
    private String key = "value";
    private String data = "data-default";
    public String getData() {
        return data;
    }
    private List<Integer> integers = new ArrayList<>();
    public void setData(String data) {
        if (data.equals("data")) {
            this.data = "data-nodata";
        } else {
            this.data = data;
        }
    }


    @Override
    public String toString() {
        return "Data{" +
                "key='" + key + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}

@JsonIgnore
class Row {
    @JsonProperty
    private String key = "This is Row-key";

    private boolean date = false;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isDate() {
        return date;
    }

    public void setDate(boolean date) {
        this.date = date;
    }
}

class Col {
    @JsonPropertyIgnore
    private String key = "This is key";
    private int date = 96;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
