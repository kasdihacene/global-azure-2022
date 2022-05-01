package com.azure.monitoring.domain.service;

import com.azure.monitoring.application.mappers.PostResponseMapper;
import com.azure.monitoring.application.port.in.RetrievePostsUseCase;
import com.azure.monitoring.application.port.out.RetrievePostsPort;
import com.azure.monitoring.domain.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public record PostService(RetrievePostsPort retrievePostsPort, PostResponseMapper postResponseMapper)
        implements RetrievePostsUseCase {

    @Override
    public List<Post> fetchAllPosts(Integer limit) {

        log.info("Fetching all published posts");
        return postResponseMapper.map(retrievePostsPort.fetchPosts(limit));
    }
}
