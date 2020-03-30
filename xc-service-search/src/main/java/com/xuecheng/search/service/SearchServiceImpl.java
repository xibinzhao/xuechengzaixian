package com.xuecheng.search.service;

import com.xuecheng.api.search.service.SearchService;
import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Value("${xuecheng.course.index}")
    private String index;
    @Value("${xuecheng.course_media.index}")
    private String mediaIndex;
    @Value("${xuecheng.course.type}")
    private String type;
    @Value("${xuecheng.course.source_field}")
    private String sourceFiled;

    @Override
    public QueryResponseResult list(Integer page, Integer size, CourseSearchParam courseSearchParam) {
        if (courseSearchParam != null) {
            //创建查询请求
            SearchRequest searchRequest = new SearchRequest(index);
            searchRequest.types(type);
            //创建查询对象
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            //设置过滤
            String[] split = sourceFiled.split(",");
            searchSourceBuilder.fetchSource(split, null);
            //设置分页
            if (page <= 0) {
                page = 1;
            }
            if (size <= 0) {
                size = 12;
            }
            page = (page - 1) * size;
            searchSourceBuilder.from(page);
            searchSourceBuilder.size(size);
            //创建布尔查询对象
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.preTags("<span style='color: red' >");//设置前缀
            highlightBuilder.postTags("</span>");//设置后缀
            highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
            // highlightBuilder.fields().add(new HighlightBuilder.Field("description"));
            searchSourceBuilder.highlighter(highlightBuilder);
            //创建multi查询对象
            if (StringUtils.isNotEmpty(courseSearchParam.getKeyword())) {
                MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(courseSearchParam.getKeyword(), "name", "description", "teachplan").
                        minimumShouldMatch("70%").field("name", 10);
                boolQueryBuilder.must(multiMatchQueryBuilder);
            }
            if (StringUtils.isNotEmpty(courseSearchParam.getGrade())) {
                TermQueryBuilder grade = QueryBuilders.termQuery("grade", courseSearchParam.getGrade());
                boolQueryBuilder.filter(grade);
            }
            if (StringUtils.isNotEmpty(courseSearchParam.getMt())) {
                TermQueryBuilder mt = QueryBuilders.termQuery("mt", courseSearchParam.getMt());
                boolQueryBuilder.filter(mt);
            }
            if (StringUtils.isNotEmpty(courseSearchParam.getSt())) {
                TermQueryBuilder st = QueryBuilders.termQuery("st", courseSearchParam.getSt());
                boolQueryBuilder.filter(st);
            }
            //绑定查询对象
            searchSourceBuilder.query(boolQueryBuilder);
            searchRequest.source(searchSourceBuilder);
            //查询
            QueryResult queryResult = new QueryResult();
            ArrayList<CoursePub> list = new ArrayList<>();
            queryResult.setList(list);
            try {
                SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
                //获取查询结果
                SearchHits hits = searchResponse.getHits();
                queryResult.setTotal(hits.getTotalHits());
                SearchHit[] hits1 = hits.getHits();
                for (SearchHit hit : hits1) {
                    CoursePub coursePub = new CoursePub();
                    coursePub.setId(hit.getId());
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    String name = (String) sourceAsMap.get("name");
                    coursePub.setName(name);
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    if (highlightFields != null) {
                        HighlightField nameField = highlightFields.get("name");
                        if (nameField != null) {
                            Text[] fragments = nameField.getFragments();
                            StringBuffer stringBuffer = new StringBuffer();
                            for (Text str : fragments) {
                                stringBuffer.append(str.string());
                            }
                            coursePub.setName(stringBuffer.toString());
                        }
                    }
                    String pic = (String) sourceAsMap.get("pic");
                    coursePub.setPic(pic);
                    Double price = null;
                    if (sourceAsMap.get("price") != null) {
                        price = (Double) sourceAsMap.get("price");
                    }
                    coursePub.setPrice(price);
                    Double priceOld = null;
                    if (sourceAsMap.get("price_old") != null) {
                        priceOld = (Double) sourceAsMap.get("price_old");
                    }
                    coursePub.setPrice_old(priceOld);
                    list.add(coursePub);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
            return queryResponseResult;
        }
        return null;
    }

    @Override
    public Map<String, CoursePub> getAll(String courseId) {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("id", courseId));
        searchRequest.source(searchSourceBuilder);
        HashMap<String, CoursePub> map = new HashMap<>();
        try {
            SearchResponse search = restHighLevelClient.search(searchRequest);
            SearchHits result = search.getHits();
            SearchHit[] hits = result.getHits();
            for (SearchHit hit : hits) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                String id = (String) sourceAsMap.get("id");
                String name = (String) sourceAsMap.get("name");
                String grade = (String) sourceAsMap.get("grade");
                String charge = (String) sourceAsMap.get("charge");
                String pic = (String) sourceAsMap.get("pic");
                String description = (String) sourceAsMap.get("description");
                String teachplan = (String) sourceAsMap.get("teachplan");
                CoursePub coursePub = new CoursePub();
                coursePub.setId(id);
                coursePub.setName(name);
                coursePub.setPic(pic);
                coursePub.setGrade(grade);
                coursePub.setTeachplan(teachplan);
                coursePub.setDescription(description);
                map.put(courseId, coursePub);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<TeachplanMediaPub> findByCourseId(String courseId) {
        SearchRequest searchRequest = new SearchRequest(mediaIndex);
        searchRequest.types(type);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("courseid", courseId));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = null;
        try {
            search = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits result = search.getHits();
        SearchHit[] hits = result.getHits();
        ArrayList<TeachplanMediaPub> teachplanMediaPubs = new ArrayList<>();
        for (SearchHit hit : hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            TeachplanMediaPub teachplanMediaPub = new TeachplanMediaPub();
            String teachplanId = (String) sourceAsMap.get("teachplan_id");
            String courseid = (String) sourceAsMap.get("courseid");
            String media_url = (String) sourceAsMap.get("media_url");
            String media_fileoriginalname = (String) sourceAsMap.get("media_fileoriginalname");
            String media_id = (String) sourceAsMap.get("media_id");
            teachplanMediaPub.setMediaId(media_id);
            teachplanMediaPub.setTeachplanId(teachplanId);
            teachplanMediaPub.setMediaUrl(media_url);
            teachplanMediaPub.setCourseId(courseid);
            teachplanMediaPub.setMediaFileOriginalName(media_fileoriginalname);
            teachplanMediaPubs.add(teachplanMediaPub);
        }
        return teachplanMediaPubs;
    }

    @Override
    public TeachplanMediaPub getMedia(String teachplanId) {
        SearchRequest searchRequest = new SearchRequest(mediaIndex);
        searchRequest.types(type);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("teachplan_id", teachplanId));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = null;
        try {
            search = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits result = search.getHits();
        SearchHit[] hits = result.getHits();
        TeachplanMediaPub teachplanMediaPub = new TeachplanMediaPub();
        for (SearchHit hit : hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String teachplan_id = (String) sourceAsMap.get("teachplan_id");
            String courseid = (String) sourceAsMap.get("courseid");
            String media_url = (String) sourceAsMap.get("media_url");
            String media_fileoriginalname = (String) sourceAsMap.get("media_fileoriginalname");
            String media_id = (String) sourceAsMap.get("media_id");
            teachplanMediaPub.setMediaId(media_id);
            teachplanMediaPub.setTeachplanId(teachplanId);
            teachplanMediaPub.setMediaUrl(media_url);
            teachplanMediaPub.setCourseId(courseid);
            teachplanMediaPub.setMediaFileOriginalName(media_fileoriginalname);
        }
        return teachplanMediaPub;
    }
}
