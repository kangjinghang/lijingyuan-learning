package top.lijingyuan.elasticsearch.learning;

import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * DeleteIndexTest
 * <p>
 * 删除索引
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-06-19
 * @since 1.0.0
 */
@Log4j2
public class DeleteIndexTest {

    public static void main(String[] args) throws Exception {
        try (
                RestHighLevelClient client = new RestHighLevelClient(
                        RestClient.builder(new HttpHost("localhost", 9200, "http")))
        ) {
            DeleteIndexRequest request = new DeleteIndexRequest("user");
            AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
            log.info("删除索引，isAcknowledged:{}", response.isAcknowledged());
            log.info("删除索引，isFragment:{}", response.isFragment());
        }
    }

}
