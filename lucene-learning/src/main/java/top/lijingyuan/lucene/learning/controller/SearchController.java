package top.lijingyuan.lucene.learning.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import top.lijingyuan.lucene.learning.pojo.ResultModel;
import top.lijingyuan.lucene.learning.service.SearchService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/list")
public class SearchController {

    @Resource
    private SearchService searchService;

    /**
     * 搜索
     *
     * @param queryString 查询的关键字
     * @param price       查询价格范围
     * @param page        当前页
     */
    @RequestMapping
    public String query(String queryString, String price, Integer page, Model model) throws Exception {

        // 处理当前页
        if (StringUtils.isEmpty(page)) {
            page = 1;
        }
        if (page <= 0) {
            page = 1;
        }

        // 调用service查询
        ResultModel resultModel = searchService.query(queryString, price, page);
        model.addAttribute("result", resultModel);


        // 查询条件回显到页面
        model.addAttribute("queryString", queryString);
        model.addAttribute("price", price);
        model.addAttribute("page", page);
        return "search";
    }
}
