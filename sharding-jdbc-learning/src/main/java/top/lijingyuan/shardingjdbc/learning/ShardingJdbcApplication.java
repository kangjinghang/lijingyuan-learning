package top.lijingyuan.shardingjdbc.learning;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ShardingJdbcApplication
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2022-05-19
 * @since 1.0.0
 */
@SpringBootApplication
@MapperScan("top.lijingyuan.shardingjdbc.learning.mapper")
public class ShardingJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcApplication.class, args);
    }

}
