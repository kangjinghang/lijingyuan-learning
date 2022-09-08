package top.lijingyuan.tool;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import lombok.Data;

import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * fp cube
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-06-28
 * @since 1.0.0
 */
public class FpCheckDemo {

    @Data
    static class Portfolio implements Comparable<Portfolio> {
        String symbol;
        Long uid;
        Long createdAt;
        Long updatedAt;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Portfolio portfolio = (Portfolio) o;

            if (symbol != null ? !symbol.equals(portfolio.symbol) : portfolio.symbol != null) return false;
            return uid != null ? uid.equals(portfolio.uid) : portfolio.uid == null;
        }

        @Override
        public int hashCode() {
            int result = symbol != null ? symbol.hashCode() : 0;
            result = 31 * result + (uid != null ? uid.hashCode() : 0);
            return result;
        }

        @Override
        public int compareTo(Portfolio o) {
            return Long.valueOf(this.createdAt - o.createdAt).intValue();
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Portfolio.class.getSimpleName() + "[", "]")
                    .add("symbol='" + symbol + "'")
                    .add("uid=" + uid)
                    .add("createdAt=" + Instant.ofEpochMilli(createdAt).atZone(ZoneId.systemDefault()).toLocalDateTime())
                    .add("updatedAt=" + Instant.ofEpochMilli(updatedAt).atZone(ZoneId.systemDefault()).toLocalDateTime())
                    .toString();
        }
    }

    public static void main(String[] args) throws Exception {
        final CsvReader reader = CsvUtil.getReader();
        final CsvData fpData = reader.read(Paths.get(Objects.requireNonNull(FpCheckDemo.class.getClassLoader().getResource("fp_data.csv")).getPath()).toFile());
        final CsvData notFoundData = reader.read(Paths.get(Objects.requireNonNull(FpCheckDemo.class.getClassLoader().getResource("not_found_data.csv")).getPath()).toFile());
        System.out.println(fpData.getRowCount());
        System.out.println(notFoundData.getRowCount());

        List<Portfolio> list = new ArrayList<>(fpData.getRowCount());
        for (CsvRow row : fpData) {
            Portfolio p = new Portfolio();
            p.setUid(Long.valueOf(row.get(0)));
            p.setSymbol(row.get(1));
            p.setCreatedAt(Long.valueOf(row.get(2)));
            p.setUpdatedAt(Long.valueOf(row.get(3)));
            list.add(p);
        }

        List<Portfolio> notFoundList = new ArrayList<>(fpData.getRowCount());
        for (CsvRow row : notFoundData) {

            final Long uid = Long.valueOf(row.get(0));
            final String symbol = row.get(1);
            for (Portfolio portfolio : list) {
                if (portfolio.getSymbol().equals(symbol) && portfolio.getUid().equals(uid)) {
                    notFoundList.add(portfolio);
                }
            }
        }
        System.out.println(notFoundList.size());
//        notFoundList.sort((o1, o2) -> Long.valueOf(o2.createdAt - o1.getCreatedAt()).intValue());
        notFoundList.sort((o1, o2) -> Long.valueOf(o1.createdAt - o2.getCreatedAt()).intValue());
//        for (int i = 0; i < notFoundList.size(); i++) {
        String sql_format_1 = "DELETE FROM portfolio_stock_%s WHERE uid = %s AND symbol = '%s';";
        String sql_format_2 = "DELETE FROM portfolio_stock_follow_%s WHERE uid = %s AND symbol = '%s';";
        List<String> sqllist = new ArrayList<>(notFoundList.size() * 2);
        String curlFormat = "curl --silent --location --request GET 'http://localhost:8080/cancel.json?symbols=%s&uid=%s'";
        for (int i = 0; i < notFoundList.size(); i++) {
            final Portfolio portfolio = notFoundList.get(i);
//            System.out.println(i + "\t" + portfolio.toString());
//            final String sql1 = String.format(sql_format_1, portfolio.getUid() % 256, portfolio.getUid(), portfolio.getSymbol());
//            System.out.println(sql1);
//            final String sql2 = String.format(sql_format_2, (Math.abs(portfolio.getSymbol().hashCode())) % 256, portfolio.getUid(), portfolio.getSymbol());
//            System.out.println(sql2);
//            sqllist.add(sql1);
//            sqllist.add(sql2);
            String curl = String.format(curlFormat, portfolio.getSymbol(), portfolio.getUid());
//            System.out.println(curl);
            sqllist.add(curl);
        }

        FileUtil.writeLines(sqllist, "follower_del.txt", Charset.defaultCharset());

    }

}
