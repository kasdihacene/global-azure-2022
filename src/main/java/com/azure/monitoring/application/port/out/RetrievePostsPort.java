package com.azure.monitoring.application.port.out;

import com.azure.monitoring.infrastructure.adapters.apiadapter.post.PostResponse;

import java.util.List;

public interface RetrievePostsPort {
    List<PostResponse> fetchPosts(Integer limit);
}
