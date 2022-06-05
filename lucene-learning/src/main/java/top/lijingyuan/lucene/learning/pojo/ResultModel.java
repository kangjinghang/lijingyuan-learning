package top.lijingyuan.lucene.learning.pojo;

import lombok.Data;

import java.util.List;

/**
 * 自定义分页实体类
 */
@Data
public class ResultModel {

    // 商品列表
    private List<Sku> skuList;
    // 商品总数
    private Long recordCount;
    // 总页数
    private Long pageCount;
    // 当前页
    private long curPage;

}
