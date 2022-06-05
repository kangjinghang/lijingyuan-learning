package top.lijingyuan.lucene.learning.service;

import top.lijingyuan.lucene.learning.pojo.ResultModel;


public interface SearchService {

    ResultModel query(String queryString, String price, Integer page) throws Exception;
}
