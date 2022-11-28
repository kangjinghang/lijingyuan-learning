package top.lijingyuan.hadoop.learning.mapreduce.mapjoin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import top.lijingyuan.hadoop.learning.mapreduce.reduceJoin.TableBean;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * MapJoinDriver
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-11-26
 * @since 1.0.0
 */
public class MapJoinDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
        Job job = Job.getInstance(new Configuration());

        job.setJarByClass(MapJoinDriver.class);
        job.setMapperClass(MapJoinMapper.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(TableBean.class);

        job.addCacheFile(new URI("file:///Users/kangjinghang/test/hadoop/input/tablecache/pd.txt"));
        job.setNumReduceTasks(0);

        FileInputFormat.setInputPaths(job, new Path("/Users/kangjinghang/test/hadoop/input/inputtable2"));
        FileOutputFormat.setOutputPath(job, new Path("/Users/kangjinghang/test/hadoop/output11"));

        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : 1);
    }
}
