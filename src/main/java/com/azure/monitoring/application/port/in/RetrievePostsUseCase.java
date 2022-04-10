package com.azure.monitoring.application.port.in;

import com.azure.monitoring.domain.Post;

import java.util.List;

public interface RetrievePostsUseCase {
    List<Post> fetchAllPosts();
}
