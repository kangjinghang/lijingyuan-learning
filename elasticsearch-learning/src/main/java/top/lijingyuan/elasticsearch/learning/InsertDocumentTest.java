package top.lijingyuan.elasticsearch.learning;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * InsertDocumentTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-06-19
 * @since 1.0.0
 */
@Log4j2
public class InsertDocumentTest {

    public static void main(String[] args) throws Exception {
        try (
                RestHighLevelClient client = new RestHighLevelClient(
                        RestClient.builder(new HttpHost("localhost", 9200, "http")))
        ) {
            // 创建索引
            IndexRequest request = new IndexRequest();
            request.index("user").id("1001");

            User user = new User();
            user.setName("张三");
            user.setAge(30);
            user.setSex("男");

            // 转为json格式
            ObjectMapper objectMapper = new ObjectMapper();
            String userJson = objectMapper.writeValueAsString(user);
            request.source(userJson, XContentType.JSON);

            // 插入数据
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            log.info("插入文档，result:{}", response.getResult());
        }
    }

}
