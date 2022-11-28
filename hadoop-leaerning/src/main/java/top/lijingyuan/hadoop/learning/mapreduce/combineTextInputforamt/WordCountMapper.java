package top.lijingyuan.hadoop.learning.mapreduce.combineTextInputforamt;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * WordCountMapper
 * <p>
 * KEYIN, map 阶段输入的key的类型：LongWritable
 * VALUEIN,map 阶段输入value类型：Text
 * KEYOUT,map 阶段输出的Key类型：Text
 * VALUEOUT,map 阶段输出的value类型：IntWritable
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-11-25
 * @since 1.0.0
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private Text outK = new Text();
    private IntWritable outV = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        // 1. 获取一行
        // atguigu atguigu
        String line = value.toString();

        // 2. 切割
        // atguigu
        // atguigu
        final String[] words = line.split(" ");

        // 3. 循环写出
        for (String word : words) {
            // 封装 outk
            outK.set(word);
            // 写出
            context.write(outK, outV);
        }
    }
}
