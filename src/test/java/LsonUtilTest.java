import bean.ObjectParserTestBean;
import cn.luern0313.lson.LsonParser;
import cn.luern0313.lson.LsonUtil;
import com.google.gson.Gson;
import org.junit.Test;

public class LsonUtilTest {

    @Test
    public void Test(){
        Gson gson = new Gson();
        ObjectParserTestBean objectParserTestBean1 = new ObjectParserTestBean();
        objectParserTestBean1.initMap();
        String json = gson.toJson(objectParserTestBean1);
        System.out.println(json);
        System.out.println(gson.fromJson(json, ObjectParserTestBean.class));
        System.out.println(LsonUtil.fromJson(LsonParser.parseString(json), ObjectParserTestBean.class));
    }
}
