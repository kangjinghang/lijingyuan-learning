package top.lijingyuan.elasticsearch.learning;

import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpHost;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * UpdateDocumentTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-19
 * @since 1.0.0
 */
@Log4j2
public class UpdateDocumentTest {

    public static void main(String[] args) throws Exception {
        try (
                RestHighLevelClient client = new RestHighLevelClient(
                        RestClient.builder(new HttpHost("localhost", 9200, "http")))
        ) {
            UpdateRequest request = new UpdateRequest();
            request.index("user").id("1001");
            request.doc(XContentType.JSON, "sex", "女");

            // 修改数据
            UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
            log.info("修改文档，result:{}", response.getResult());
        }
    }

}
