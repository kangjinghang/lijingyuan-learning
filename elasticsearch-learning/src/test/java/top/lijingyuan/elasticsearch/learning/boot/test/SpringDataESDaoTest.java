package top.lijingyuan.elasticsearch.learning.boot.test;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;
import top.lijingyuan.elasticsearch.learning.boot.Product;
import top.lijingyuan.elasticsearch.learning.boot.ProductDao;

import java.util.ArrayList;
import java.util.List;

/**
 * SpringDataESDaoTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-06-20
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataESDaoTest {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 新增
     */
    @Test
    public void save() {
        Product product = new Product();
        product.setId(2L);
        product.setTitle("华为手机");
        product.setCategory("手机");
        product.setPrice(2999.0);
        product.setImages("http://www.atguigu/hw.jpg");
        productDao.save(product);
    }

    //修改
    @Test
    public void update() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("小米 2 手机");
        product.setCategory("手机");
        product.setPrice(9999.0);
        product.setImages("http://www.atguigu/xm.jpg");
        productDao.save(product);
    }

    //根据 id 查询
    @Test
    public void findById() {
        Product product = productDao.findById(1L).get();
        System.out.println(product);
    }

    //查询所有
    @Test
    public void findAll() {
        Iterable<Product> products = productDao.findAll();
        for (Product product : products) {
            System.out.println(product);
        }
    }

    //删除
    @Test
    public void delete() {
        Product product = new Product();
        product.setId(1L);
        productDao.delete(product);
    }

    //批量新增
    @Test
    public void saveAll() {
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setId(Long.valueOf(i));
            product.setTitle("[" + i + "]小米手机");
            product.setCategory("手机");
            product.setPrice(1999.0 + i);
            product.setImages("http://www.atguigu/xm.jpg");
            productList.add(product);
        }
        productDao.saveAll(productList);
    }

    //分页查询
    @Test
    public void findByPageable() {
        //设置排序(排序方式，正序还是倒序，排序的 id)
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        int currentPage = 0;//当前页，第一页从 0 开始，1 表示第二页
        int pageSize = 5;//每页显示多少条
        //设置查询分页
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize, sort);
        //分页查询
        Page<Product> productPage = productDao.findAll(pageRequest);
        for (Product Product : productPage.getContent()) {
            System.out.println(Product);
        }
    }

    /**
     * term 查询
     * search(termQueryBuilder) 调用搜索方法，参数查询构建器对象
     */
    @Test
    public void termQuery() {
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "小米");
        Criteria criteria = new Criteria("title").is("小米");
        Query query = new CriteriaQuery(criteria);
        SearchHits<Product> hits = elasticsearchRestTemplate.search(query, Product.class);
        List<SearchHit<Product>> searchHits = hits.getSearchHits();
        for (SearchHit<Product> searchHit : searchHits) {
            System.out.println(searchHit.getContent());
        }
    }

    /**
     * term 查询加分页
     */
    @Test
    public void termQueryByPage() {
        int currentPage = 0;
        int pageSize = 5;
        //设置查询分页
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize);
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "小米");
//        Iterable<Product> products = productDao.search(termQueryBuilder, pageRequest);
//        for (Product product : products) {
//            System.out.println(product);
//        }
        Criteria criteria = new Criteria("title").is("小米");
        Query query = new CriteriaQuery(criteria);
        SearchHits<Product> hits = elasticsearchRestTemplate.search(query, Product.class);
        List<SearchHit<Product>> searchHits = hits.getSearchHits();
        for (SearchHit<Product> searchHit : searchHits) {
            System.out.println(searchHit.getContent());
        }
    }

}
