package top.lijingyuan.hbase.learning;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

/**
 * HBaseConnection
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-11-24
 * @since 1.0.0
 */
public class HBaseConnection {

    public static Connection connection = null;

    static {
        System.setProperty("hadoop.home.dir", "/Users/kangjinghang/.m2/repository/org/apache/hadoop/hadoop-common/3.1.3");

        // 1.创建配置对象
//        Configuration conf = new Configuration();
        // 2.添加配置参数
//        conf.set("hbase.zookeeper.quorum", "localhost");
        // 3.创建 hbase 的连接
        // 默认使用同步连接
        try {
//            connection = ConnectionFactory.createConnection(conf);
            connection = ConnectionFactory.createConnection();
        } catch (IOException e) {
            System.out.println("连接获取失败");
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭方法,用于进程关闭时调用
     */
    public static void closeConnection() throws IOException {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(connection);
        closeConnection();
    }

    public static void getSingleConnection(String[] args) throws IOException {

        System.setProperty("hadoop.home.dir", "/Users/kangjinghang/.m2/repository/org/apache/hadoop/hadoop-common/3.1.3");

        // 1.创建配置对象
        Configuration conf = new Configuration();
        // 2.添加配置参数
        conf.set("hbase.zookeeper.quorum", "localhost");
        // 3.创建 hbase 的连接
        // 默认使用同步连接
        Connection connection = ConnectionFactory.createConnection(conf);
        // 可以使用异步连接
        // 主要影响后续的 DML 操作
//        CompletableFuture<AsyncConnection> asyncConnection =
//                ConnectionFactory.createAsyncConnection(conf);
        // 4.使用连接
        System.out.println(connection);
        // 5.关闭连接
        connection.close();
    }

}

