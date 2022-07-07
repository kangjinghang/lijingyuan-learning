package top.lijingyuan.elasticsearch.learning;

import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * BatchDeleteDocumentTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-19
 * @since 1.0.0
 */
@Log4j2
public class BatchDeleteDocumentTest {

    public static void main(String[] args) throws Exception {
        try (
                RestHighLevelClient client = new RestHighLevelClient(
                        RestClient.builder(new HttpHost("localhost", 9200, "http")))
        ) {
            // 批量删除数据
            BulkRequest request = new BulkRequest();
            request.add(new DeleteRequest().index("user").id("1001"));
            request.add(new DeleteRequest().index("user").id("1002"));
            request.add(new DeleteRequest().index("user").id("1003"));

            BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);

            log.info("批量插入文档，took:{}", response.getTook());
            log.info("批量插入文档，ingestTook:{}", response.getIngestTook());
            log.info("批量插入文档，ingestTookInMillis:{}", response.getIngestTookInMillis());
            for (BulkItemResponse item : response.getItems()) {
                log.info("批量插入文档，items:{}", item);
            }

        }
    }

}
