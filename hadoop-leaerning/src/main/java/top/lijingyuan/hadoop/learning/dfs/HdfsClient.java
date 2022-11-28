package top.lijingyuan.hadoop.learning.dfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

/**
 * Hdfs Client
 * <p>hdfs web: http://hadoop6:9870/
 * <p>yarn web: http://hadoop7:8088/cluster
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-11-23
 * @since 1.0.0
 */
public class HdfsClient {

    private FileSystem fs;

    @Before
    public void init() throws IOException, URISyntaxException, InterruptedException {
        System.setProperty("hadoop.home.dir", "/Users/kangjinghang/.m2/repository/org/apache/hadoop/hadoop-common/3.1.3");


        // 1. 获取文件系统
        Configuration configuration = new Configuration();

        fs = FileSystem.get(new URI("hdfs://hadoop6:8020"), configuration, "kangjinghang");
    }

    @After
    public void close() throws IOException {
        // 3. 关闭资源
        fs.close();
    }

    /**
     * 创建目录
     */
    @Test
    public void testMkdirs() throws IOException {
        // 2. 创建目录
        fs.mkdirs(new Path("/xiyou/huaguoshan/"));
    }

    /**
     * 上传
     * 参数优先级排序：（1）客户端代码中设置的值 >（2）ClassPath下的用户自定义配置文件 >（3）然后是服务器的自定义配置（xxx-site.xml） >（4）服务器的默认配置（xxx-default.xml）
     */
    @Test
    public void testPut() throws IOException {
        final URL resource = this.getClass().getClassLoader().getResource("sunwukong.txt");
        assert resource != null;
        fs.copyFromLocalFile(false, true, new Path(resource.getPath()), new Path("/xiyou/huaguoshan"));
    }

    /**
     * 下载
     */
    @Test
    public void testGet() throws IOException {
        fs.copyToLocalFile(false, new Path("/xiyou/huaguoshan"), new Path("/Users/kangjinghang/test/temp"), true);
    }

    /**
     * 删除
     */
    @Test
    public void testRm() throws IOException {
        fs.delete(new Path("/xiyou/huaguoshan/sunwukong.txt"), false);
    }

    /**
     * 更名和移动
     */
    @Test
    public void testMv() throws IOException {
        fs.rename(new Path("/xiyou/huaguoshan/sunwukong.txt"), new Path("/xiyou/huaguoshan/sunwukong2.txt"));
    }

    /**
     * 查看文件名称、权限、长度、块信息
     */
    @Test
    public void testListFiles() throws IOException {
        // 2. 获取文件详情
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);

        while (listFiles.hasNext()) {
            LocatedFileStatus fileStatus = listFiles.next();

            System.out.println("========" + fileStatus.getPath() + "=========");
            System.out.println(fileStatus.getPermission());
            System.out.println(fileStatus.getOwner());
            System.out.println(fileStatus.getGroup());
            System.out.println(fileStatus.getLen());
            System.out.println(fileStatus.getModificationTime());
            System.out.println(fileStatus.getReplication());
            System.out.println(fileStatus.getBlockSize());
            System.out.println(fileStatus.getPath().getName());

            // 获取块信息
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            System.out.println(Arrays.toString(blockLocations));
        }
    }

    /**
     * 文件和文件夹判断
     */
    @Test
    public void testListStatus() throws IOException {
        // 2. 判断是文件还是文件夹
        FileStatus[] listStatus = fs.listStatus(new Path("/"));

        for (FileStatus fileStatus : listStatus) {

            // 如果是文件
            if (fileStatus.isFile()) {
                System.out.println("f:"+fileStatus.getPath().getName());
            }else {
                System.out.println("d:"+fileStatus.getPath().getName());
            }
        }
    }

}
