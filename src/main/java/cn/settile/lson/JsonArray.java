package cn.settile.lson;

import cn.settile.lson.core.DefaultSerializer;
import cn.settile.lson.exception.JsonSerializeException;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author lzjyzq2
 * @date 2020-08-07 21:54:35
 */
public class JsonArray extends Json implements List<Object> {

    private final List<Object> list;

    public JsonArray(){
        list = new ArrayList<>();
    }

    public JsonArray(List<Object> list) {
        this.list = list;
    }




    //------------------------------------------------------------
    // 以下为 Lis<Object> 接口实现
    //------------------------------------------------------------

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<Object> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return list.toArray(ts);
    }

    @Override
    public boolean add(Object o) {
        return list.add(o);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return list.containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<?> collection) {
        return list.add(collection);
    }

    @Override
    public boolean addAll(int i, Collection<?> collection) {
        return list.addAll(i,collection);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return list.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return list.retainAll(collection);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public Object get(int i) {
        return list.get(i);
    }

    @Override
    public Object set(int i, Object o) {
        return list.set(i,o);
    }

    @Override
    public void add(int i, Object o) {
        list.add(i,o);
    }

    @Override
    public Object remove(int i) {
        return list.remove(i);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<Object> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<Object> listIterator(int i) {
        return list.listIterator(i);
    }

    @Override
    public List<Object> subList(int i, int i1) {
        return list.subList(i,i1);
    }

    @Override
    public void forEach(Consumer<? super Object> action) {
        list.forEach(action);
    }

//    @Override
//    public Stream<Object> stream() {
//        return list.stream();
//    }


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
