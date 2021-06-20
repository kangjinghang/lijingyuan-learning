package top.lijingyuan.elasticsearch.learning.boot.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.test.context.junit4.SpringRunner;
import top.lijingyuan.elasticsearch.learning.boot.Product;

/**
 * SpringDataESIndexTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-06-20
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataESIndexTest {

    //注入 ElasticsearchRestTemplate
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    //创建索引并增加映射配置
    @Test
    public void createIndex() {
        //创建索引，系统初始化会自动创建索引
        System.out.println("创建索引");
    }

    @Test
    public void deleteIndex() {
        //创建索引，系统初始化会自动创建索引
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(Product.class);
        boolean flag = indexOperations.delete();
        System.out.println("删除索引 = " + flag);
    }

}
