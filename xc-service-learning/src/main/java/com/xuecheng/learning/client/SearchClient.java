package com.xuecheng.learning.client;

import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("XC-SEARCH-SERVICE")
@Component
public interface SearchClient {
    @GetMapping("/search/course/getMedia/{id}/")
    TeachplanMediaPub getMedia(@PathVariable("id") String teachplanId);
}
