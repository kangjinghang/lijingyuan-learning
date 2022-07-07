package top.lijingyuan.elasticsearch.learning;

import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * DeleteDocumentTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-19
 * @since 1.0.0
 */
@Log4j2
public class DeleteDocumentTest {

    public static void main(String[] args) throws Exception {
        try (
                RestHighLevelClient client = new RestHighLevelClient(
                        RestClient.builder(new HttpHost("localhost", 9200, "http")))
        ) {
            DeleteRequest request = new DeleteRequest();
            request.index("user").id("1001");

            // 删除数据
            DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
            log.info("删除文档，response:{}", response);
        }
    }

}
