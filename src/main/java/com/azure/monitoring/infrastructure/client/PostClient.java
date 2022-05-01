package com.azure.monitoring.infrastructure.client;

import com.azure.monitoring.infrastructure.apiadapter.post.PostResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "jsonplaceholder-client", url = "${post.base-url}")
public interface PostClient {

    @GetMapping(value = "/jsonserver/v1/posts")
    List<PostResponse> getPosts();
}
