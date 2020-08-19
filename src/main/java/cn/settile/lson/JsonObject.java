package cn.settile.lson;

import cn.settile.lson.core.DefaultSerializer;
import cn.settile.lson.exception.JsonSerializeException;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * @author lzjyzq2
 * @date 2020-08-07 21:35:45
 */
@SuppressWarnings("unchecked")
public class JsonObject extends Json implements Map<String,Object> {

    private final Map<String,Object> map;

    public JsonObject(Map<String, Object> map){
        if(map==null) {
            throw new IllegalArgumentException("map is null!");
        }
        this.map = map;
    }
    public JsonObject(){
        map = new LinkedHashMap<>();
    }







    //------------------------------------------------------------
    // 以下为 Map 接口实现
    //------------------------------------------------------------

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object o) {
        return map.containsKey(o);
    }

    @Override
    public boolean containsValue(Object o) {
        return map.containsValue(o);
    }

    @Override
    public Object get(Object o) {
        return map.get(o);
    }

    @Override
    public Object put(String key, Object value) {
        return map.put(key,value);
    }

    @Override
    public Object remove(Object o) {
        return map.remove(o);
    }

    @Override
    public void putAll(Map<? extends String,?> map) {
        this.map.putAll(map);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set keySet() {
        return map.keySet();
    }

    @Override
    public Collection values() {
        return map.values();
    }

    @Override
    public Set<Entry<String,Object>> entrySet() {
        return map.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return map.equals(o);
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    @Override
    public void forEach(BiConsumer<? super String, ? super Object> action) {
        map.forEach(action);
    }

    @Override
    public String toString() {
        try {
            return DefaultSerializer.toJsonString(this);
        } catch (JsonSerializeException e) {
            e.printStackTrace();
        }
        return null;
    }

}
