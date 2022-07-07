package top.lijingyuan.metrics.timer;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-09-03
 * @since 1.0.0
 */
public class TestJson {

    public static void main(String[] args) throws Exception {
        URL resource = ResourceUtil.getResource("anno.json");
        String str = FileUtil.readString(resource, "utf-8");
        JSONArray jsonArray = JSONUtil.parseArray(str);
        System.out.println(jsonArray);
        List<String> list = new ArrayList<>();
        for (Object o : jsonArray) {
            JSONObject json = (JSONObject)o;
            list.add(json.getStr("announcementTitle"));
        }
        System.out.println(list.size());
    }

}
