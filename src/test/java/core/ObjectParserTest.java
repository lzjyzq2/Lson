package core;

import bean.ObjectParserTestBean;
import cn.luern0313.lson.core.ObjectParser;
import com.google.gson.Gson;
import org.junit.Test;

public class ObjectParserTest {
    @Test
    public void Test() throws Exception {

        Gson gson = new Gson();
        ObjectParserTestBean objectParserTestBean1 = new ObjectParserTestBean();
        objectParserTestBean1.initMap();
        String json = gson.toJson(objectParserTestBean1);
        ObjectParser objectParser = new ObjectParser();
        ObjectParserTestBean objectParserTestBean = gson.fromJson(json, ObjectParserTestBean.class);
        System.out.println(objectParserTestBean.getMap().getClass());
        System.out.println(objectParser.toObject(json, ObjectParserTestBean.class));
    }
}

