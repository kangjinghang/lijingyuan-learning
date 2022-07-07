package top.lijingyuan.elasticsearch.learning;

import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.MaxAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

/**
 * QueryDocumentTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-19
 * @since 1.0.0
 */
@Log4j2
public class QueryDocumentTest {

    public static void main(String[] args) throws Exception {
        try (
                RestHighLevelClient client = new RestHighLevelClient(
                        RestClient.builder(new HttpHost("localhost", 9200, "http")))
        ) {
            // 查询数据
            SearchRequest request = new SearchRequest();
            request.indices("user");

//            SearchSourceBuilder query = queryAll();
//            SearchSourceBuilder query = queryByCondition();
//            SearchSourceBuilder query = queryByPage(1);
//            SearchSourceBuilder query = queryByPage(2);
//            SearchSourceBuilder query = queryByOrder();
//            SearchSourceBuilder query = queryAndFilter();
//            SearchSourceBuilder query = combinedQuery();
//            SearchSourceBuilder query = fuzzyQuery();
//            SearchSourceBuilder query = highLightQuery();
//            SearchSourceBuilder query = juheQuery();
            SearchSourceBuilder query = groupQuery();
            request.source(query);

            SearchResponse response = client.search(request, RequestOptions.DEFAULT);

            SearchHits hits = response.getHits();
//            log.info("Query documents,totalHits:{}", hits.getTotalHits());
            log.info("Query documents,took:{}", response.getTook());
            log.info("Query documents,response:{}", response);
            for (SearchHit hit : hits) {
                log.info("Query documents,hit:{}", hit);
            }
        }
    }

    private static SearchSourceBuilder queryAll() {
        // 全量查询
        return new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
    }

    private static SearchSourceBuilder queryByCondition() {
        // 条件查询
        return new SearchSourceBuilder().query(QueryBuilders.termQuery("age", 30));
    }

    private static SearchSourceBuilder queryByPage(int page) {
        // 分页查询
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        builder.from((page - 1) * 2);
        builder.size(2);
        return builder;
    }

    private static SearchSourceBuilder queryByOrder() {
        // 排序查询
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        builder.sort("age", SortOrder.DESC);
        return builder;
    }

    private static SearchSourceBuilder juheQuery() {
        // 聚合查询
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        MaxAggregationBuilder aggregationBuilder = AggregationBuilders.max("maxAge").field("age");
        builder.aggregation(aggregationBuilder);
        return builder;
    }

    private static SearchSourceBuilder groupQuery() {
        // 分组查询
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("ageGroup").field("age");
        builder.aggregation(termsAggregationBuilder);
        return builder;
    }

    private static SearchSourceBuilder queryAndFilter() {
        // 查询后过滤字段
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        builder.sort("age", SortOrder.DESC);
        String[] includes = {};
        String[] excludes = {"name"};
        builder.fetchSource(includes, excludes);
        return builder;
    }

    private static SearchSourceBuilder fuzzyQuery() {
        // 模糊查询
        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("name", "wangwu").fuzziness(Fuzziness.ONE);
        return new SearchSourceBuilder().query(fuzzyQueryBuilder);
    }

    private static SearchSourceBuilder highLightQuery() {
        // 高亮查询
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "zhangsan");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(termQueryBuilder);
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("name");
        searchSourceBuilder.highlighter(highlightBuilder);
        return searchSourceBuilder;
    }

    private static SearchSourceBuilder combinedQuery() {
        // 组合查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        boolQueryBuilder.must(QueryBuilders.matchQuery("age", 30));
//        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("sex", "男"));

        // 或
//        boolQueryBuilder.should(QueryBuilders.matchQuery("age", 30));
//        boolQueryBuilder.should(QueryBuilders.matchQuery("age", 40));
        //范围查询
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age");
        rangeQueryBuilder.gte(30);
        rangeQueryBuilder.lte(40);

        return new SearchSourceBuilder().query(rangeQueryBuilder);
    }

}
