package com.xuecheng.search;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.text.Highlighter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = SearchApplication.class)
@RunWith(SpringRunner.class)
public class TestSearch {
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private RestClient rsetClient;

    @Test
    public void testSearchAll() throws IOException {
        //创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest();
        //创建搜索请求构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //给搜索请求构建对象进行 条件封装
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        String[] strings = {"name", "studymodel"};
        searchSourceBuilder.fetchSource(strings, null);
        //进行绑定
        searchRequest.source(searchSourceBuilder);
        //请求发送  得到返回结果
        SearchResponse search = client.search(searchRequest);
        //获取搜索应该得到的内容
        SearchHits hits = search.getHits();
        //获取到符合搜索的所有条数
        long totalHits = hits.getTotalHits();
        //获取实际搜索得到的内容
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit searchHit : hits1) {
            //数据的id值
            String id = searchHit.getId();
            System.out.println(id);
            //获取到源数据
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
    }

    @Test
    public void testSearchPage() throws IOException {
        //创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest();
        //创建搜索请求构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //给搜索请求构建对象进行 条件封装
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        String[] strings = {"name", "studymodel"};
        //设置包含那些字段与不包含那些字段
        searchSourceBuilder.fetchSource(strings, null);
        //设置分页参数
        int page = 1;
        int size = 2;
        int form = (page - 1) * size;
        //设置分页的起始值   从0开始
        searchSourceBuilder.from(form);
        //设置分页的条数
        searchSourceBuilder.size(size);
        //进行绑定
        searchRequest.source(searchSourceBuilder);
        //请求发送  得到返回结果
        SearchResponse search = client.search(searchRequest);
        //获取搜索应该得到的内容
        SearchHits hits = search.getHits();
        //获取到符合搜索的所有条数
        long totalHits = hits.getTotalHits();
        //获取实际搜索得到的内容
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit searchHit : hits1) {
            //数据的id值
            String id = searchHit.getId();
            System.out.println(id);
            //获取到源数据
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
    }

    //Term查询为精确匹配  不会进行分词之后再查询
    @Test
    public void testTermSearch() throws IOException {
        //创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest();
        //创建搜索请求构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //给搜索请求构建对象进行 条件封装
        searchSourceBuilder.query(QueryBuilders.termQuery("name", "spring"));
        String[] strings = {"name", "studymodel"};
        //设置包含那些字段与不包含那些字段
        searchSourceBuilder.fetchSource(strings, null);
        //设置分页参数
        int page = 1;
        int size = 2;
        int form = (page - 1) * size;
        //设置分页的起始值   从0开始
        searchSourceBuilder.from(form);
        //设置分页的条数
        searchSourceBuilder.size(size);
        //进行绑定
        searchRequest.source(searchSourceBuilder);
        //请求发送  得到返回结果
        SearchResponse search = client.search(searchRequest);
        //获取搜索应该得到的内容
        SearchHits hits = search.getHits();
        //获取到符合搜索的所有条数
        long totalHits = hits.getTotalHits();
        //获取实际搜索得到的内容
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit searchHit : hits1) {
            //数据的id值
            String id = searchHit.getId();
            System.out.println(id);
            //获取到源数据
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
    }

    @Test
    public void testTermSearchById() throws IOException {
        //创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest();
        //创建搜索请求构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //给搜索请求构建对象进行 条件封装
        String[] ids = {"1", "2", "1000"};
        searchSourceBuilder.query(QueryBuilders.termsQuery("_id", ids));
        String[] strings = {"name", "studymodel"};
        //设置包含那些字段与不包含那些字段
        searchSourceBuilder.fetchSource(strings, null);
        //设置分页参数
        int page = 1;
        int size = 2;
        int form = (page - 1) * size;
        //设置分页的起始值   从0开始
        searchSourceBuilder.from(form);
        //设置分页的条数
        searchSourceBuilder.size(size);
        //进行绑定
        searchRequest.source(searchSourceBuilder);
        //请求发送  得到返回结果
        SearchResponse search = client.search(searchRequest);
        //获取搜索应该得到的内容
        SearchHits hits = search.getHits();
        //获取到符合搜索的所有条数
        long totalHits = hits.getTotalHits();
        //获取实际搜索得到的内容
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit searchHit : hits1) {
            //数据的id值
            String id = searchHit.getId();
            System.out.println(id);
            //获取到源数据
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
    }

    @Test
    public void testMatchSearch() throws IOException {
        //创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest();
        //创建搜索请求构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //给搜索请求构建对象进行 条件封装
        String[] ids = {"1", "2", "1000"};
        searchSourceBuilder.query(QueryBuilders.matchQuery("description", "spring开发框架").minimumShouldMatch("80%"));
        //设置分页参数
        int page = 1;
        int size = 2;
        int form = (page - 1) * size;
        //设置分页的起始值   从0开始
        searchSourceBuilder.from(form);
        //设置分页的条数
        searchSourceBuilder.size(size);
        //进行绑定
        searchRequest.source(searchSourceBuilder);
        //请求发送  得到返回结果
        SearchResponse search = client.search(searchRequest);
        //获取搜索应该得到的内容
        SearchHits hits = search.getHits();
        //获取到符合搜索的所有条数
        long totalHits = hits.getTotalHits();
        //获取实际搜索得到的内容
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit searchHit : hits1) {
            //数据的id值
            String id = searchHit.getId();
            System.out.println(id);
            //获取到源数据
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
    }

    @Test
    public void testMultiMatchSearch() throws IOException {
        //创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest();
        //创建搜索请求构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //给搜索请求构建对象进行 条件封装
        String[] ids = {"1", "2", "1000"};
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("spring css", "description", "name").minimumShouldMatch("50%").field("name", 10));
        //设置分页参数
        int page = 1;
        int size = 1;
        int form = (page - 1) * size;
        //设置分页的起始值   从0开始
        searchSourceBuilder.from(form);
        //设置分页的条数
        searchSourceBuilder.size(size);
        //进行绑定
        searchRequest.source(searchSourceBuilder);
        //请求发送  得到返回结果
        SearchResponse search = client.search(searchRequest);
        //获取搜索应该得到的内容
        SearchHits hits = search.getHits();
        //获取到符合搜索的所有条数
        long totalHits = hits.getTotalHits();
        //获取实际搜索得到的内容
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit searchHit : hits1) {
            //数据的id值
            String id = searchHit.getId();
            System.out.println(id);
            //获取到源数据
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
    }

    @Test
    public void testBooleanSearch() throws IOException {
        //创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest();
        //创建搜索请求构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //给搜索请求构建对象进行 条件封装
        String[] ids = {"1", "2", "1000"};
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("spring css", "description", "name").
                minimumShouldMatch("50%").field("name", 10);
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("studymodel", "201001");
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(termQueryBuilder);
        boolQueryBuilder.must(multiMatchQueryBuilder);
        searchSourceBuilder.query(boolQueryBuilder);
        //设置分页参数
        int page = 1;
        int size = 1;
        int form = (page - 1) * size;
        //设置分页的起始值   从0开始
        searchSourceBuilder.from(form);
        //设置分页的条数
        searchSourceBuilder.size(size);
        //进行绑定
        searchRequest.source(searchSourceBuilder);
        //请求发送  得到返回结果
        SearchResponse search = client.search(searchRequest);
        //获取搜索应该得到的内容
        SearchHits hits = search.getHits();
        //获取到符合搜索的所有条数
        long totalHits = hits.getTotalHits();
        //获取实际搜索得到的内容
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit searchHit : hits1) {
            //数据的id值
            String id = searchHit.getId();
            System.out.println(id);
            //获取到源数据
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
    }

    @Test
    public void testFilterSearch() throws IOException {
        //创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest();
        //创建搜索请求构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //给搜索请求构建对象进行 条件封装
        String[] ids = {"1", "2", "1000"};
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("spring css", "description", "name").
                minimumShouldMatch("50%").field("name", 10);
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("studymodel", "201001");
        RangeQueryBuilder price = QueryBuilders.rangeQuery("price").gte(0).lte(100);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(multiMatchQueryBuilder);
        boolQueryBuilder.filter(termQueryBuilder);
        boolQueryBuilder.filter(price);
        searchSourceBuilder.query(boolQueryBuilder);
        //设置分页参数
        int page = 1;
        int size = 1;
        int form = (page - 1) * size;
        //设置分页的起始值   从0开始
        searchSourceBuilder.from(form);
        //设置分页的条数
        searchSourceBuilder.size(size);
        //进行绑定
        searchRequest.source(searchSourceBuilder);
        //请求发送  得到返回结果
        SearchResponse search = client.search(searchRequest);
        //获取搜索应该得到的内容
        SearchHits hits = search.getHits();
        //获取到符合搜索的所有条数
        long totalHits = hits.getTotalHits();
        //获取实际搜索得到的内容
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit searchHit : hits1) {
            //数据的id值
            String id = searchHit.getId();
            System.out.println(id);
            //获取到源数据
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
    }

    @Test
    public void testSortSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        RangeQueryBuilder price = QueryBuilders.rangeQuery("price").gte(0).lte(100);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.filter(price);
        searchSourceBuilder.query(boolQueryBuilder);
        int page = 1;
        int size = 3;
        int form = (page - 1) * size;
        searchSourceBuilder.from(form);
        searchSourceBuilder.size(size);
        searchSourceBuilder.sort("studymodel", SortOrder.DESC);
        searchSourceBuilder.sort("price", SortOrder.ASC);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest);
        SearchHits hits = search.getHits();
        long totalHits = hits.getTotalHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit searchHit : hits1) {
            //数据的id值
            String id = searchHit.getId();
            System.out.println(id);
            //获取到源数据
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
    }

    @Test
    public void testHightSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "price", "description"},
                new String[]{});
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        MultiMatchQueryBuilder multiMatchBuilder = QueryBuilders.multiMatchQuery("开发", "name", "description").field("name", 10);
        RangeQueryBuilder price = QueryBuilders.rangeQuery("price").gte(0).lte(100);
        boolQueryBuilder.filter(price);
        boolQueryBuilder.must(multiMatchBuilder);
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.sort("price", SortOrder.ASC);
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<tag>");//设置前缀
        highlightBuilder.postTags("</tag>");//设置后缀
        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
        // highlightBuilder.fields().add(new HighlightBuilder.Field("description"));
        searchSourceBuilder.highlighter(highlightBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest);
        SearchHits hits = search.getHits();
        long totalHits = hits.getTotalHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit searchHit : searchHits) {
            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
            if (highlightFields != null) {
                HighlightField name = highlightFields.get("name");
                if (name != null) {
                    Text[] fragments = name.getFragments();
                    StringBuffer name1 = null;
                    for (Text text : fragments) {
                        name1.append(text);
                    }
                    System.out.println(name1);
                }
            }
            String id = searchHit.getId();
            System.out.println(id);
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
    }

    @Test
    public void testHighlight() throws IOException {
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "price", "description"},
                new String[]{});
        searchRequest.source(searchSourceBuilder);
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("开发", "name", "description");
        searchSourceBuilder.query(multiMatchQueryBuilder);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(searchSourceBuilder.query());
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(0).lte(100));
        searchSourceBuilder.sort(new FieldSortBuilder("studymodel").order(SortOrder.DESC));
        searchSourceBuilder.sort(new FieldSortBuilder("price").order(SortOrder.ASC));
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<tag>");//设置前缀
        highlightBuilder.postTags("</tag>");//设置后缀
        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
        // highlightBuilder.fields().add(new HighlightBuilder.Field("description"));
        searchSourceBuilder.highlighter(highlightBuilder);
        SearchResponse searchResponse = client.search(searchRequest);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //名称
            String name = (String) sourceAsMap.get("name");
            //取出高亮字段内容
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (highlightFields != null) {
                HighlightField nameField = highlightFields.get("name");
                if (nameField != null) {
                    Text[] fragments = nameField.getFragments();
                    StringBuffer stringBuffer = new StringBuffer();
                    for (Text str : fragments) {
                        stringBuffer.append(str.string());
                    }
                    name = stringBuffer.toString();
                }
            }
            String index = hit.getIndex();
            String type = hit.getType();
            String id = hit.getId();
            float score = hit.getScore();
            String sourceAsString = hit.getSourceAsString();
            String studymodel = (String) sourceAsMap.get("studymodel");
            String description = (String) sourceAsMap.get("description");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
        }
    }
}
