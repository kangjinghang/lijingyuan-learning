package top.lijingyuan.tool;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-12-02
 * @since 1.0.0
 */
public class DT {

    static class KLine {
        double open;
        double close;
        double low;
        double high;
        int amount;

        public KLine(double open, double close, double low, double high, int amount) {
            this.open = open;
            this.close = close;
            this.low = low;
            this.high = high;
            this.amount = amount;
        }
    }

    /*
     * 存储计算过程变量
     */
    static class Storage {

        /**
         * 全部数据的分价分隔数，100根线
         */
        int separateAmount;

        /**
         * 一个流通周期内的最高价
         */
        double chipMax;

        /**
         * 一个流通周期内的最低价
         */
        double chipMin;

        /**
         * 每一级分价价格，上下两根线价格差
         */
        double chipStep;

        /**
         * 分价价格数组
         */
        double[] chipSeparate;

        /**
         * 单根K线筹码分布存储
         */
        Map<Double, Integer>[] chipStorage;

        // 默认separateAmount=100
        public Storage(int separateAmount, int days) {
            this.separateAmount = separateAmount;
            chipSeparate = new double[separateAmount];
            chipStorage = new Map[days];
        }
    }

    /*
     * 计算一根K线的筹码分布，按照三角形分布算法计算出输入数据的单日筹码
     * @param {array} data 输入数据
     */

    public static void calcSingleDayChip(Storage storage, KLine[] data) {
        // 遍历输入数据
        for (int i = 0; i < data.length; i++) {
            final KLine currKline = data[i];
            int minIndex = getPosition(storage.chipSeparate, currKline.low);
            int maxIndex = getPosition(storage.chipSeparate, currKline.high);
            int delta = maxIndex - minIndex;
            int midIndex = delta / 2 + minIndex;
            // 总数
            int totalLength = getTriangleAmount(minIndex, midIndex, maxIndex);

            /*
             * 单日分价赋值
             */

            int currLineLength = 1;
            // key为价格，value为成交量
            Map<Double, Integer> dayChip = new LinkedHashMap<>();
            for (int j = minIndex; j < midIndex; j++) {
                double price = storage.chipSeparate[j];
                //  triangleCount / totalLength = 当前横线占比所有横线长度的比例
                // 把当日的成交量按比例分出去了
                dayChip.put(price, currKline.amount * currLineLength / totalLength);
                currLineLength++;
            }
            for (int j = midIndex; j <= maxIndex; j++) {
                double price = storage.chipSeparate[j];
                // 经过转换后的成交额，叠加上成交额和所占比例，做成三角形的横线
                dayChip.put(price, currKline.amount * currLineLength / totalLength);
                currLineLength--;
            }
            storage.chipStorage[i] = dayChip;
            System.out.println();
            System.out.println();
            System.out.println("chipStorage-------------");
            for (Map.Entry<Double, Integer> entry : dayChip.entrySet()) {
                for (int j = 0; j < entry.getValue(); j++) {
                    System.out.print("  ");
//                    System.out.print("-");
                }
                System.out.print("price:" + entry.getKey());
                System.out.println();
            }
            System.out.println("chipStorage-------------");
            System.out.println();
            System.out.println();
        }
    }

    /*
     * 流通股本
     */
    public static int circulationAmount = 3000;

    /*
     * 计算某一日的累计筹码分布
     *
     * @param {array} data 输入数据
     * @param {number} index 计算哪一天的筹码分布
     */
    public static Map<Double, Integer> calcDayChip(Storage storage, KLine[] data, int index) {
        int i, sumAmount;
        // 计算几日对等流通股本,确定起始索引
        sumAmount = 0;
        // 计算流通股本内的最大值最小值
        storage.chipMax = storage.chipMin = data[index].high;
        for (i = index; i >= 0 && sumAmount < circulationAmount; i--) {
            sumAmount += data[i].amount;
            if (storage.chipMax < data[i].high) { // 重新赋值，周期为当前之前
                storage.chipMax = data[i].high;
            }
            if (storage.chipMin > data[i].low) { // 重新赋值，周期为当前之前
                storage.chipMin = data[i].low;
            }
        }
        i++;
        System.out.println("i = " + i);
        // 初始化当日的筹码分价区间
        Map<Double, Integer> chipData = new LinkedHashMap<>();
        int minIndex = getPosition(storage.chipSeparate, storage.chipMin);
        // 底部的最低价
        double dayPrice = storage.chipSeparate[minIndex];
        System.out.println(minIndex);
        // 初始化chipData，key为价格
        while (dayPrice <= storage.chipMax) {
            chipData.put(dayPrice, 0);
            minIndex++; // 向上移动
            if (minIndex >= storage.separateAmount) {
                break;
            }
            dayPrice = storage.chipSeparate[minIndex];
        }
        // 叠加筹码交易量
        for (; i <= index; i++) {
            final Map<Double, Integer> dayChip = storage.chipStorage[i];
            for (Map.Entry<Double, Integer> entry : dayChip.entrySet()) {
                final Double price = entry.getKey();
                final Integer lineAmount = dayChip.get(price);
                // 交易量累计
                chipData.put(price, chipData.get(price) + lineAmount);
            }
        }
        System.out.println("========= ==================     result ==================================== ==============");
        for (Map.Entry<Double, Integer> entry : chipData.entrySet()) {
            for (int j = 0; j < entry.getValue(); j++) {
                System.out.print("-");
            }
//            System.out.print("price:" + entry.getKey());
            System.out.println();
        }
        return chipData;
    }

    /* min = 0,mid = 1, max = 3
     * 计算当日三角形形状
     */
    public static int getTriangleAmount(int minIndex, int midIndex, int maxIndex) {
        if ((maxIndex - minIndex) % 2 == 0) {
            // 三角形
            int a = maxIndex - minIndex + 2;
            int h = midIndex - minIndex + 1;
            // totalLength = 三角形分布总量
            int totalLength = a * h / 2;
            System.out.println("totalLength=" + totalLength);
            return totalLength;
        } else {
            // 梯形
            int a = maxIndex - minIndex + 2;
            int h = midIndex - minIndex + 1;
            int totalLength = (a + 1) * h / 2;
            System.out.println("totalLength=" + totalLength);
            return totalLength;
        }
    }

    /**
     * 在总分价数组中定位价格索引，二分搜索，定位到是位于哪根线上的
     *
     * @param arr   有序数组表
     * @param price 股价
     * @return 定位价格索引
     */
    public static int getPosition(double[] arr, double price) {
        int i;
        for (i = 0; i < arr.length; i++) {
            // 当前的横线 >= 价格
            if (arr[i] >= price) {
                return i;
            }
        }
        // show not reach here
        throw new IllegalArgumentException("price is too high or too low");
    }

    /**
     * 计算整个数据段（流通股本范围的周期，60天）的总分加数组（O(n)），确定以什么样的精度来细分分价表
     *
     * @param data 输入数据
     */
    public static Storage calcSeparate(KLine[] data) {
        Storage storage = new Storage(100, data.length);
        storage.chipMax = data[0].high;
        storage.chipMin = data[0].low;
        for (int i = 1; i < data.length; i++) {
            if (storage.chipMax < data[i].high) {
                storage.chipMax = data[i].high;
            }
            if (storage.chipMin > data[i].low) {
                storage.chipMin = data[i].low;
            }
        }
        System.out.println("max = " + storage.chipMax + " ,min = " + storage.chipMin);
        storage.chipStep = (storage.chipMax - storage.chipMin) / storage.separateAmount;
        for (int i = 0; i < storage.separateAmount - 1; i++) {
            storage.chipSeparate[i] = storage.chipMin + i * storage.chipStep;
        }
        storage.chipSeparate[storage.separateAmount - 1] = storage.chipMax;
        System.out.println(Arrays.asList(storage.chipSeparate));
        return storage;
    }

    public static void main(String[] args) {
        KLine kline1 = new KLine(3.89, 3.89, 3.86, 3.93, 300);
        KLine kline2 = new KLine(3.88, 3.85, 3.81, 3.99, 320);
        KLine kline3 = new KLine(3.85, 3.91, 3.82, 3.95, 260);
        KLine kline4 = new KLine(3.89, 4.02, 3.89, 4.07, 250);
        KLine kline5 = new KLine(4.04, 4.05, 4.00, 4.08, 280);
        KLine kline6 = new KLine(4.05, 4.00, 3.98, 4.08, 320);
        KLine kline7 = new KLine(4.00, 4.00, 3.97, 4.04, 330);
        KLine kline8 = new KLine(3.99, 3.90, 3.88, 4.00, 350);
        KLine kline9 = new KLine(3.89, 3.90, 3.88, 3.92, 310);
        KLine kline10 = new KLine(3.89, 3.98, 3.88, 3.98, 260);
        KLine kline11 = new KLine(3.99, 3.98, 3.95, 4.03, 280);
        KLine kline12 = new KLine(3.98, 4.06, 3.96, 4.08, 240);
        KLine kline13 = new KLine(4.08, 4.08, 4.02, 4.08, 200);
        KLine[] input = new KLine[]{kline1, kline2, kline3, kline4, kline5, kline6, kline7, kline8, kline9, kline10, kline11, kline12, kline13};
        final Storage storage = calcSeparate(input);
        calcSingleDayChip(storage, input);
        // 3.81 4.08
        // 计算最后一天的筹码分布
        calcDayChip(storage, input, input.length - 1);
    }

}
