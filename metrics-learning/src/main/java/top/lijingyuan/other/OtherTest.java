package top.lijingyuan.other;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * OtherTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-06-08
 * @since 1.0.0
 */
public class OtherTest {

    public static void main(String[] args) {
        String today = DateUtil.today();
        System.out.println(today);
        DateTime date = DateUtil.parseDate(today);
        System.out.println(date);
        System.out.println(date.getTime());

        Date start = new Date(1622974200462L);
        System.out.println(start);

        String regex = "[\\[\\]]";
        String lText = "公告及通告 - [更換董事或重要行政職能或職責的變更 / 更換行政總裁 / 更換審核委員會成員 / 更換薪酬委員會成員 / 更換公司秘書 / 停牌]";
//        String lText = "交易所買賣基金的交易資料";
//        String lText = "";
        List<String> list = Splitter.on(Pattern.compile(regex)).splitToList(lText);
        System.out.println(list.size());
        list.forEach(System.out::println);
        if (list.size() > 1) {
            String allTitle = list.get(1);
            List<String> t2Titles = Lists.newArrayList();
            Splitter.on('/').split(allTitle).forEach(t -> t2Titles.add(t.trim()));
            t2Titles.forEach(System.out::println);
//            List<String> t2Titles = Splitter.on('/').splitToStream(allTitle).map(String::trim).collect(Collectors.toList());
//            t2Titles.forEach(System.out::println);

        }
    }

}
