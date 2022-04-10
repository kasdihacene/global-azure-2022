package com.azure.monitoring.infrastructure.apiadapter;

import com.azure.monitoring.application.port.out.RetrievePostsPort;
import com.azure.monitoring.infrastructure.apiadapter.post.PostResponse;
import com.azure.monitoring.infrastructure.config.PostProperties;
import com.azure.monitoring.infrastructure.exceptions.PostAdapterException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Component
public record PostAdapter(RestTemplate restTemplate, PostProperties postProperties) implements RetrievePostsPort {

    @Override
    public List<PostResponse> fetchPosts() {
        try {
            ResponseEntity<PostResponse[]> postResponses = restTemplate
                    .getForEntity(URI.create(postProperties.getUrlGetAll()), PostResponse[].class);

            return ofNullable(postResponses.getBody())
                    .map(posts -> Arrays
                            .stream(posts)
                            .toList())
                    .orElse(emptyList());

        } catch (Exception exception) {
            throw new PostAdapterException(String.format("An Error occurred when calling the API [%s] | Error [%s]", postProperties.getUrlGetAll(), exception.getMessage()));
        }
    }

}
