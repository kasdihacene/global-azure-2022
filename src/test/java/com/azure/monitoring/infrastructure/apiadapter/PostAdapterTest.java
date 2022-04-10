package com.azure.monitoring.infrastructure.apiadapter;

import com.azure.monitoring.application.port.out.RetrievePostsPort;
import com.azure.monitoring.infrastructure.apiadapter.post.PostResponse;
import com.azure.monitoring.infrastructure.config.PostProperties;
import com.azure.monitoring.infrastructure.exceptions.PostAdapterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class PostAdapterTest {

    private RestTemplate restTemplate;
    private PostProperties postProperties;
    private RetrievePostsPort retrievePostsPort;

    @BeforeEach
    public void setup() {

        postProperties = Mockito.mock(PostProperties.class);
        restTemplate = Mockito.mock(RestTemplate.class);

        retrievePostsPort = new PostAdapter(restTemplate, postProperties);
    }

    @Test
    void should_fetch_all_published_posts_per_user() {
        // Given
        String simulatedUrl = "https://url/to/call";

        // When
        Mockito.when(postProperties.getUrlGetAll()).thenReturn(simulatedUrl);
        Mockito.when(restTemplate.getForEntity(any(), any())).thenReturn(new ResponseEntity<>(somePostResponses(), HttpStatus.OK));

        List<PostResponse> postResponses = retrievePostsPort.fetchPosts();

        // Then
        List<PostResponse> listPosts = expectedPostResponses();
        assertThat(postResponses).containsAnyElementsOf(listPosts);
    }

    @Test
    void should_throw_an_exception_when_an_error_occurs() {
        // Given
        String simulatedUrl = "https://url/to/call";

        // When
        Mockito.when(postProperties.getUrlGetAll()).thenReturn(simulatedUrl);
        Mockito.when(restTemplate.getForEntity(any(), any())).thenThrow(new RuntimeException("ERROR SIMULATION"));

        assertThatThrownBy(() -> retrievePostsPort.fetchPosts())
                // Then
                .isInstanceOf(PostAdapterException.class)
                .hasMessageContaining("An Error occurred when calling the API [https://url/to/call] | Error [ERROR SIMULATION]");

    }

    @Test
    void should_throw_an_exception_when_the_social_network_provider_returns_an_error() {
        // Given
        String simulatedUrl = "https://url/to/call";

        // When
        Mockito.when(postProperties.getUrlGetAll()).thenReturn(simulatedUrl);
        Mockito.when(restTemplate.getForEntity(eq(URI.create(simulatedUrl)), any())).thenThrow(new RuntimeException("EXCEPTION SIMULATION"));

        assertThatThrownBy(() -> retrievePostsPort.fetchPosts())
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

    private PostResponse[] somePostResponses() {
        return new PostResponse[]{PostResponse.builder()
                .id(3)
                .userId(1)
                .title("ea molestias quasi exercitationem repellat qui ipsa sit aut")
                .body("et iusto sed quo iurenvoluptatem occaecati omnis eligendi aut")
                .build()};
    }
}
