package com.azure.monitoring.infrastructure.apiadapter;

import com.azure.monitoring.application.port.out.RetrievePostsPort;
import com.azure.monitoring.infrastructure.apiadapter.post.PostResponse;
import com.azure.monitoring.infrastructure.client.PostClient;
import com.azure.monitoring.infrastructure.config.PostProperties;
import com.azure.monitoring.infrastructure.exceptions.PostAdapterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
@Slf4j
public record PostAdapter(PostClient postClient, PostProperties postProperties) implements RetrievePostsPort {

    @Override
    public List<PostResponse> fetchPosts(Integer limit) {
        try {
            log.info("Calling endpoint : {}", postProperties.getUrlGetAll());
            List<PostResponse> posts = postClient.getPosts();
            return IntStream.range(0, limit)
                    .mapToObj(posts::get)
                    .toList();

        } catch (Exception exception) {
            throw new PostAdapterException(String.format("An Error occurred when calling the API [%s] | Error [%s]", postProperties.getUrlGetAll(), exception.getMessage()));
        }
    }

}
