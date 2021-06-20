package top.lijingyuan.elasticsearch.learning;

import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

/**
 * SearchIndexTest
 * <p>
 * 查询索引
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-06-19
 * @since 1.0.0
 */
@Log4j2
public class SearchIndexTest {

    public static void main(String[] args) throws Exception {
        try (
                RestHighLevelClient client = new RestHighLevelClient(
                        RestClient.builder(new HttpHost("localhost", 9200, "http")))
        ) {
            GetIndexRequest request = new GetIndexRequest("user");
            GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
            log.info("查询索引，aliases:{}", response.getAliases());
            log.info("查询索引，settings:{}", response.getSettings());
            log.info("查询索引，mappings:{}", response.getMappings());
            log.info("查询索引，dataStreams:{}", response.getDataStreams());
            log.info("查询索引，defaultSettings:{}", response.getDefaultSettings());
            log.info("查询索引，indices:{}", response.getIndices());
        }
    }

}
