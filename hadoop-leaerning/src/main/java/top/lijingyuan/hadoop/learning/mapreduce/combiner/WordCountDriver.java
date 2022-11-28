package top.lijingyuan.hadoop.learning.mapreduce.combiner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * WordCountDriver
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-11-25
 * @since 1.0.0
 */
public class WordCountDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        // 1.获取job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2.设置jar包路径
        job.setJarByClass(WordCountDriver.class);

        // 3.关联mapper和reducer
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        // 4.设置map输出的kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 5.设置最终输出的kV类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setCombinerClass(WordCountCombiner.class);

        // 6.设置输入路径和输出路径
        FileInputFormat.setInputPaths(job, new Path("/Users/kangjinghang/test/hadoop/myinput"));
        FileOutputFormat.setOutputPath(job, new Path("/Users/kangjinghang/test/hadoop/output8"));

        // 7.提交job
        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 : 1);
    }

}