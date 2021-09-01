package top.lijingyuan.metrics;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Test
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-21
 * @since 1.0.0
 */
@Slf4j
public class Test {

    public static void main(String[] args) {
//        generateJson();
//        System.out.println(jsonArray.stream().map(JSON.class::cast).map(json -> (String) json.getByPath("symbol"))
//                .collect(Collectors.joining(",")));
//        URL resource = ResourceUtil.getResource("stock.json");
//        String str = FileUtil.readString(resource, "utf-8");
//        JSONArray jsonArray = JSONUtil.parseArray(str);
//        System.out.println(jsonArray.size());
//        System.out.println(jsonArray.stream().map(JSON.class::cast).map(json -> (String) json.getByPath("symbol"))
//                .collect(Collectors.joining(",")));

//        System.out.println(DateUtil.beginOfMonth(new Date()));
        log.error("this is a error");
        String symbol = "aa";
        try {
            int i = 1 / 0;
        } catch (Exception e){
            log.error("refreshLastedCikBySymbol error",e.toString());
            log.error("refreshLastedCikBySymbol symbol = {} , error={}",symbol,e);
        }
    }

    private static void generateJson() {
        URL resource = ResourceUtil.getResource("t2Code.json");
        String str = FileUtil.readString(resource, "utf-8");
        JSONArray jsonArray = JSONUtil.parseArray(str);
        System.out.println(jsonArray.size());
        List<JSONObject> list = jsonArray.stream().map(JSONObject.class::cast).collect(Collectors.toList());
        for (JSONObject json : list) {
            String t1CodeStr = "HKT1CodeClassifyEnum.";
            Integer t1code = json.getInt("t1code");
            switch (t1code) {
                case 10000:
                    t1CodeStr += "GGJTG";
                    break;
                case 20000:
                    t1CodeStr += "TH";
                    break;
                case 30000:
                    t1CodeStr += "SSWJ";
                    break;
                case 40000:
                    t1CodeStr += "CWBB";
                    break;
                case 50000:
                    t1CodeStr += "YRPLBB";
                    break;
                case 51500:
                    t1CodeStr += "YBB";
                    break;
                case 52000:
                    t1CodeStr += "WRDBZG";
                    break;
                case 53000:
                    t1CodeStr += "GSZLB";
                    break;
                case 54000:
                    t1CodeStr += "XZWJ";
                    break;
                case 70000:
                    t1CodeStr += "ZQ";
                    break;
                case 80000:
                    t1CodeStr += "JJJYZL";
                    break;
                case 90000:
                    t1CodeStr += "JGGG";
                    break;
                case 51000:
                    t1CodeStr += "GFGHGG";
                    break;
                case 55000:
                    t1CodeStr += "JYPL";
                    break;
                case 81000:
                    t1CodeStr += "GGJYZL";
                    break;
            }
            System.out.println("t2(" + json.getInt("code") + "," + json.getInt("t2Gcode") + ",\"" + json.getStr("name") + "\"," + t1CodeStr + "),");
        }
    }

}
