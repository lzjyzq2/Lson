package bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class ObjectParserTestBean {
    private String key = "This is Key";
    private BigDecimal decimal = new BigDecimal(123);
    private BigInteger bigInteger = new BigInteger("6961111111111111111111111");
    private Data data = new Data();

    private Map<String,String> map;
    private Map<String, Data> dataMap;


    public void initMap(){
        map = new HashMap();
        map.put("key1","value1");
        map.put("key2","value2");
        map.put("key3","value3");
        map.put("key4","value4");

        dataMap = new HashMap<>();
        dataMap.put("dataKey1",new Data());
        dataMap.put("dataKey2",new Data());
        dataMap.put("dataKey3",new Data());
        dataMap.put("dataKey4",new Data());
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public BigDecimal getDecimal() {
        return decimal;
    }

    public void setDecimal(BigDecimal decimal) {
        this.decimal = decimal;
    }

    public BigInteger getBigInteger() {
        return bigInteger;
    }

    public void setBigInteger(BigInteger bigInteger) {
        this.bigInteger = bigInteger;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Map<String, String> getMap() {
        return map;
    }

    @Override
    public String toString() {
        return "ObjectParserTestBean{" +
                "key='" + key + '\'' +
                ", decimal=" + decimal +
                ", bigInteger=" + bigInteger +
                ", data=" + data +
                ", map=" + map +
                ", dataMap=" + dataMap +
                '}';
    }
}

class Data{
    boolean canRead = true;

    @Override
    public String toString() {
        return "Data{" +
                "canRead=" + canRead +
                '}';
    }
}
