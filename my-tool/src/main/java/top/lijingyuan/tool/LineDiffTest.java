package top.lijingyuan.tool;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * LineDiffTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-06-14
 * @since 1.0.0
 */
public class LineDiffTest {

    public static void main(String[] args) {

        final Set<String> stockSymbols = new HashSet<>();
        final List<String> list1 = FileUtil.readLines("us_stock.txt", Charset.defaultCharset());
        for (String s : list1) {
            stockSymbols.add(s.trim());
        }

        final Set<String> tongSymbols = new HashSet<>();
        final List<String> list2 = FileUtil.readLines("us_tong.txt", Charset.defaultCharset());
        for (String s : list2) {
            tongSymbols.add(s.trim());
        }
        System.out.println(stockSymbols.size());
        System.out.println(tongSymbols.size());

        System.out.println(CollUtil.disjunction(stockSymbols, tongSymbols));

    }

}
