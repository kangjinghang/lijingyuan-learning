package top.lijingyuan.elasticsearch.learning;

import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * BatchInsertDocumentTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-19
 * @since 1.0.0
 */
@Log4j2
public class BatchInsertDocumentTest {

    public static void main(String[] args) throws Exception {
        try (
                RestHighLevelClient client = new RestHighLevelClient(
                        RestClient.builder(new HttpHost("localhost", 9200, "http")))
        ) {
            // 批量插入数据
            BulkRequest request = new BulkRequest();
            request.add(new IndexRequest().index("user").id("1001").source(XContentType.JSON, "name", "zhangsan", "age", 30, "sex", "男"));
            request.add(new IndexRequest().index("user").id("1002").source(XContentType.JSON, "name", "lisi", "age", 30, "sex", "女"));
            request.add(new IndexRequest().index("user").id("1003").source(XContentType.JSON, "name", "wangwu", "age", 40, "sex", "男"));
            request.add(new IndexRequest().index("user").id("1004").source(XContentType.JSON, "name", "wangwu1", "age", 40, "sex", "女"));
            request.add(new IndexRequest().index("user").id("1005").source(XContentType.JSON, "name", "wangwu2", "age", 50, "sex", "男"));
            request.add(new IndexRequest().index("user").id("1006").source(XContentType.JSON, "name", "wangwu3", "age", 50, "sex", "男"));

            BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);

            log.info("批量插入文档，took:{}", response.getTook());
            log.info("批量插入文档，ingestTook:{}", response.getIngestTook());
            log.info("批量插入文档，ingestTookInMillis:{}", response.getIngestTookInMillis());
            log.info("批量插入文档，items:{}", response.getItems());

        }
    }

}
