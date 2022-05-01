package com.azure.monitoring.application.service;

import com.azure.monitoring.application.mappers.PostResponseMapper;
import com.azure.monitoring.application.mappers.PostResponseMapperImpl;
import com.azure.monitoring.application.port.in.RetrievePostsUseCase;
import com.azure.monitoring.application.port.out.RetrievePostsPort;
import com.azure.monitoring.domain.Post;
import com.azure.monitoring.domain.service.PostService;
import com.azure.monitoring.infrastructure.apiadapter.post.PostResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.List;

class PostServiceTest {

    private RetrievePostsUseCase postsUseCase;
    private RetrievePostsPort retrievePostsPort;
    private PostResponseMapper postResponseMapper;

    @BeforeEach
    public void setup() {

        postResponseMapper = new PostResponseMapperImpl();
        retrievePostsPort = Mockito.mock(RetrievePostsPort.class);
        postsUseCase = new PostService(retrievePostsPort, postResponseMapper);
    }

    @Test
    void should_fetch_all_posts() {

        // Given
        List<PostResponse> postResponses = somePostResponse();
        List<Post> expectedPosts = postResponseMapper.map(postResponses);
        int limit = 10;

        // When
        Mockito.when(retrievePostsPort.fetchPosts(ArgumentMatchers.any())).thenReturn(postResponses);
        List<Post> posts = postsUseCase.fetchAllPosts(limit);

        // Then
        Assertions.assertThat(posts).containsAnyElementsOf(expectedPosts);
    }

    private List<PostResponse> somePostResponse() {
        return List.of(PostResponse.builder()
                .id(1)
                .userId(1)
                .title("some title")
                .body("some body")
                .build());
    }
}
