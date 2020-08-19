package core;

import cn.luern0313.lson.annotation.JsonIgnore;
import cn.luern0313.lson.annotation.JsonProperty;
import cn.luern0313.lson.annotation.JsonPropertyIgnore;
import cn.luern0313.lson.core.PropertyMatcher;
import com.google.gson.Gson;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
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
//        System.out.println(integers.getGenericType());
//        System.out.println(((ParameterizedType) integers.getGenericType()).getActualTypeArguments()[0]);


        PropertyMatcher propertyMatcher2 = new PropertyMatcher(new Row());
        System.out.println(propertyMatcher2);

        PropertyMatcher propertyMatcher3 = new PropertyMatcher(new Col());
        System.out.println(propertyMatcher3);

        Data data = new Data();
        Gson gson = new Gson();
        data.initAlls();
        Data otherData = gson.fromJson(gson.toJson(data),Data.class);
        System.out.println(otherData);
//        try {
//            Data data = reflectData(Data.class);
//            System.out.println(data);
//        } catch (IntrospectionException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
//            e.printStackTrace();
//        }
    }

    public <T> T reflectData(Class<T> clz) throws IntrospectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Type type = (Type) clz;
        return (T) reflect(type);
//        T t = clz.getDeclaredConstructor().newInstance();
//        Field[] fields = clz.getDeclaredFields();
//        for (Field field : fields) {
//            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clz);
//            //获得set方法
//            Method method = pd.getWriteMethod();
//            if(field.getType().equals(byte.class)){
//                method.invoke(int.class.getEnclosingConstructor().newInstance(),999);
//            }else if (field.getType().equals(short.class)){
//
//            }else if(field.getType().equals(int.class)){
//
//            }else if(field.getType().equals(long.class)){
//
//            }else if(field.getType().equals(float.class)){
//
//            }else if(field.getType().equals(double.class)){
//
//            }else if(field.getType().equals(boolean.class)){
//
//            }else if(field.getType().equals(Integer.class)){
//
//            }else if(field.getType().equals(Short.class)){
//
//            }else if(field.getType().equals(Byte.class)){
//
//            }else if(field.getType().equals(Long.class)){
//
//            }else if(field.getType().equals(Float.class)){
//
//            }else if(field.getType().equals(Double.class)){
//
//            }else if(field.getType().equals(Boolean.class)){
//
//            }else if(field.getType().equals(String.class)){
//
//            }else if(field.getType().equals(List.class)){
//
//            }else {
//                method.invoke(reflect(field.getGenericType()));
//            }
//            System.out.println(method.getName());


//            method.invoke(field.getClass().getDeclaredConstructor().newInstance(), new Object[]{"123"});
//            //获得get方法
//            Method get = pd.getReadMethod();
//            Object getValue = get.invoke(list.get(0), new Object[]{});
//            System.out.println("field:"+field.getName()+"---getValue:"+getValue);
    }

    public Object reflect(Type type) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IntrospectionException {
        Class<?> clz = (Class)type;
        System.out.println(clz);
        Object t = clz.getDeclaredConstructor().newInstance();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName());
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clz);
            //获得set方法
            Method method = pd.getWriteMethod();
            if (field.getType().equals(byte.class)) {
                method.invoke(int.class.getDeclaredConstructor().newInstance(), 999);
            } else if (field.getType().equals(short.class)) {

            } else if (field.getType().equals(int.class)) {

            } else if (field.getType().equals(long.class)) {

            } else if (field.getType().equals(float.class)) {

            } else if (field.getType().equals(double.class)) {

            } else if (field.getType().equals(boolean.class)) {

            } else if (field.getType().equals(Integer.class)) {

            } else if (field.getType().equals(Short.class)) {

            } else if (field.getType().equals(Byte.class)) {

            } else if (field.getType().equals(Long.class)) {

            } else if (field.getType().equals(Float.class)) {

            } else if (field.getType().equals(Double.class)) {

            } else if (field.getType().equals(Boolean.class)) {

            } else if (field.getType().equals(String.class)) {
                method.invoke(t,"String");
            } else if (field.getType().equals(List.class)) {

            } else {
                method.invoke(t,reflect(field.getGenericType()));
            }
            System.out.println(method.getName());
//            method.invoke(field.getClass().getDeclaredConstructor().newInstance(), new Object[]{"123"});
//            //获得get方法
//            Method get = pd.getReadMethod();
//            Object getValue = get.invoke(list.get(0), new Object[]{});
//            System.out.println("field:"+field.getName()+"---getValue:"+getValue);
        }
        return t;
    }


    /**
     * 根据属性，获取get方法
     * @param ob 对象
     * @param name 属性名
     * @return
     * @throws Exception
     */
    public static Object getGetMethod(Object ob , String name)throws Exception{
        Class f = ob.getClass().getDeclaredField(name).getType();
        Method getter;
        if(f.equals(boolean.class)||f.equals(Boolean.class)){
            getter = ob.getClass().getDeclaredMethod("is"+ getConvertName(name));

        }else {
            getter = ob.getClass().getDeclaredMethod("get"+getConvertName(name));
        }
        return getter.invoke(ob);
    }

    private static String getConvertName(String name){
        char[] chars = name.toCharArray();
        chars[0] = (char)(chars[0] - 32);
        return new String(chars);
    }


    /**
     * 根据属性，拿到set方法，并把值set到对象中
     * @param obj 对象
     * @param clazz 对象的class
     * @param filedName 需要设置值得属性
     * @param typeClass
     * @param value
     */
    public static void setValue(Object obj,Class<?> clazz,String filedName,Class<?> typeClass,Object value){
        filedName = removeLine(filedName);
        String methodName = "set" + filedName.substring(0,1).toUpperCase()+filedName.substring(1);
        try{
            Method method =  clazz.getDeclaredMethod(methodName, typeClass);
            if(method!=null){
                method.invoke(obj, getClassTypeValue(typeClass, value));
            }else {
                obj.getClass().getDeclaredField(filedName).set(obj,value);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 通过class类型获取获取对应类型的值
     * @param typeClass class类型
     * @param value 值
     * @return Object
     */
    private static Object getClassTypeValue(Class<?> typeClass, Object value){
        if(typeClass == int.class  || value instanceof Integer){
            if(null == value){
                return 0;
            }
            return value;
        }else if(typeClass == short.class){
            if(null == value){
                return 0;
            }
            return value;
        }else if(typeClass == byte.class){
            if(null == value){
                return 0;
            }
            return value;
        }else if(typeClass == double.class){
            if(null == value){
                return 0;
            }
            return value;
        }else if(typeClass == long.class){
            if(null == value){
                return 0;
            }
            return value;
        }else if(typeClass == String.class){
            if(null == value){
                return "";
            }
            return value;
        }else if(typeClass == boolean.class){
            if(null == value){
                return true;
            }
            return value;
        }else if(typeClass == BigDecimal.class){
            if(null == value){
                return new BigDecimal(0);
            }
            return new BigDecimal(value+"");
        }else {
            return typeClass.cast(value);
        }
    }

    /**
     * 处理字符串  如：  abc_dex ---> abcDex
     * @param str
     * @return
     */
    public static  String removeLine(String str){
        if(null != str && str.contains("_")){
            int i = str.indexOf("_");
            char ch = str.charAt(i+1);
            char newCh = (ch+"").substring(0, 1).toUpperCase().toCharArray()[0];
            String newStr = str.replace(str.charAt(i+1), newCh);
            String newStr2 = newStr.replace("_", "");
            return newStr2;
        }
        return str;
    }
}


class Data {
    private String key = "value";
    private String data = "data-default";
    private All[] alls = new All[20];
    public String getData() {
        return data;
    }

    public void setData(String data) {
        if (data.equals("data")) {
            this.data = "data-nodata";
        } else {
            this.data = data;
        }
    }

    public void initAlls(){
        for (int i = 0; i < alls.length; i++) {
            B b = new B();
            b.testInt = i;
            alls[i] = new All();
            alls[i].setA(b);
        }
    }

    @Override
    public String toString() {
        return "Data{" +
                "key='" + key + '\'' +
                ", data='" + data + '\'' +
                ", alls=" + Arrays.toString(alls) +
                '}';
    }
}

class All{
    private A a;

    public void setA(A a) {
        this.a = a;
    }

    public A getA() {
        return a;
    }
}

abstract class A {
    String test = "test";
}

class B extends A{
    String test2 = "test2";
    int testInt = 56;

    @Override
    public String toString() {
        return "B{" +
                "test2='" + test2 + '\'' +
                ", testInt=" + testInt +
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
