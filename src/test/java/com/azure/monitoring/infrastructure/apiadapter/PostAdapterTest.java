package com.azure.monitoring.infrastructure.apiadapter;

import com.azure.monitoring.application.port.out.RetrievePostsPort;
import com.azure.monitoring.infrastructure.apiadapter.post.PostResponse;
import com.azure.monitoring.infrastructure.client.PostClient;
import com.azure.monitoring.infrastructure.config.PostProperties;
import com.azure.monitoring.infrastructure.exceptions.PostAdapterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostAdapterTest {

    private PostClient postClient;
    private PostProperties postProperties;
    private RetrievePostsPort retrievePostsPort;

    @BeforeEach
    public void setup() {

        postProperties = Mockito.mock(PostProperties.class);
        postClient = Mockito.mock(PostClient.class);

        retrievePostsPort = new PostAdapter(postClient, postProperties);
    }

    @Test
    void should_fetch_all_published_posts_per_user() {
        // Given
        String simulatedUrl = "https://url/to/call";
        int limit = 1;

        // When
        Mockito.when(postProperties.getUrlGetAll()).thenReturn(simulatedUrl);
        Mockito.when(postClient.getPosts()).thenReturn(somePostResponses());

        List<PostResponse> postResponses = retrievePostsPort.fetchPosts(limit);

        // Then
        List<PostResponse> listPosts = expectedPostResponses();
        assertThat(postResponses).containsAnyElementsOf(listPosts);
    }

    @Test
    void should_throw_an_exception_when_an_error_occurs() {
        // Given
        String simulatedUrl = "https://url/to/call";
        int limit = 10;

        // When
        Mockito.when(postProperties.getUrlGetAll()).thenReturn(simulatedUrl);
        Mockito.when(postClient.getPosts()).thenThrow(new RuntimeException("ERROR SIMULATION"));
        assertThatThrownBy(() -> retrievePostsPort.fetchPosts(limit))
                // Then
                .isInstanceOf(PostAdapterException.class)
                .hasMessageContaining("An Error occurred when calling the API [https://url/to/call] | Error [ERROR SIMULATION]");

    }

    @Test
    void should_throw_an_exception_when_the_social_network_provider_returns_an_error() {
        // Given
        String simulatedUrl = "https://url/to/call";
        int limit = 10;

        // When
        Mockito.when(postProperties.getUrlGetAll()).thenReturn(simulatedUrl);
        Mockito.when(postClient.getPosts()).thenThrow(new RuntimeException("EXCEPTION SIMULATION"));

        assertThatThrownBy(() -> retrievePostsPort.fetchPosts(limit))
                // Then
                .isInstanceOf(PostAdapterException.class)
                .hasMessageContaining("EXCEPTION SIMULATION");

    }


    private List<PostResponse> expectedPostResponses() {
        return List.of(PostResponse.builder()
                .id(3)
                .userId(1)
                .title("ea molestias quasi exercitationem repellat qui ipsa sit aut")
                .body("et iusto sed quo iurenvoluptatem occaecati omnis eligendi aut")
                .build());
    }

    private List<PostResponse> somePostResponses() {
        return List.of(PostResponse.builder()
                .id(3)
                .userId(1)
                .title("ea molestias quasi exercitationem repellat qui ipsa sit aut")
                .body("et iusto sed quo iurenvoluptatem occaecati omnis eligendi aut")
                .build());
    }
}
