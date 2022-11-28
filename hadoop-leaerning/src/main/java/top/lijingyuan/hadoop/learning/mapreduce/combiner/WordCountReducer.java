package top.lijingyuan.hadoop.learning.mapreduce.combiner;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * WordCountReducer
 * <p>
 * KEYIN, reduce 阶段输入的key的类型：Text
 * VALUEIN,reduce 阶段输入value类型：IntWritable
 * KEYOUT,reduce 阶段输出的Key类型：Text
 * VALUEOUT,reduce 阶段输出的value类型：IntWritable
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-11-25
 * @since 1.0.0
 */
public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable outV = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {

        int sum = 0;
        // atguigu, (1,1)
        // 累加
        for (IntWritable value : values) {
            sum += value.get();
        }
        outV.set(sum);

        // 写出
        context.write(key, outV);
    }
}
