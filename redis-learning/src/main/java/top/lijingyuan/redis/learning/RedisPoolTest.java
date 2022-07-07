package top.lijingyuan.redis.learning;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.List;

/**
 * RedisPoolTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-07-26
 * @since 1.0.0
 */
@Slf4j
public class RedisPoolTest {

    public static void main(String[] args) {
        // common-pool连接池配置，这里使用默认配置，后面小节会介绍具体配置说明
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        // 设置最大连接数为默认值的5倍
        poolConfig.setMaxTotal(GenericObjectPoolConfig.DEFAULT_MAX_TOTAL * 5);
        // 设置最大空闲连接数为默认值的3倍
        poolConfig.setMaxIdle(GenericObjectPoolConfig.DEFAULT_MAX_IDLE * 3);
        // 设置最小空闲连接数为默认值的2倍
        poolConfig.setMinIdle(GenericObjectPoolConfig.DEFAULT_MIN_IDLE * 2);
        // 设置开启jmx功能
        poolConfig.setJmxEnabled(true);
        // 设置连接池没有连接后客户端的最大等待时间(单位为毫秒)
        poolConfig.setMaxWaitMillis(3000);
        // 初始化Jedis连接池
        JedisPool jedisPool = new JedisPool(poolConfig, "127.0.0.1", 6379);
        Jedis jedis = null;
        try {
            // 1. 从连接池获取jedis对象
            jedis = jedisPool.getResource();
            // 2. 执行操作
            String key = "hello";

            jedis.get(key);

            Pipeline pipeline = jedis.pipelined();
            pipeline.set(key, "world");
            pipeline.incr("counter");
            List<Object> resultList = pipeline.syncAndReturnAll();
            for (Object object : resultList) {
                log.info("result:{}", object);
            }

            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            String script = "return redis.call('get',KEYS[1])";
            Object result = jedis.eval(script, 1, key);
            log.info("script result:{}", result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                // 如果使用JedisPool，close操作不是关闭连接，代表归还连接池
                jedis.close();
            }
        }
    }

    public void mdel(List<String> keys) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 1)生成pipeline对象
        Pipeline pipeline = jedis.pipelined();
        // 2)pipeline执行命令，注意此时命令并未真正执行
        for (String key : keys) {
            pipeline.del(key);
        }
        // 3)执行命令
        pipeline.sync();
    }


}


