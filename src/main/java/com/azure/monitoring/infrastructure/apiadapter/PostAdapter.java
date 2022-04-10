package com.azure.monitoring.infrastructure.apiadapter;

import com.azure.monitoring.application.port.out.RetrievePostsPort;
import com.azure.monitoring.infrastructure.apiadapter.post.PostResponse;
import com.azure.monitoring.infrastructure.client.PostClient;
import com.azure.monitoring.infrastructure.config.PostProperties;
import com.azure.monitoring.infrastructure.exceptions.PostAdapterException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public record PostAdapter(PostClient postClient, PostProperties postProperties) implements RetrievePostsPort {

    @Override
    public List<PostResponse> fetchPosts() {
        try {

            return postClient.getPosts();

        } catch (Exception exception) {
            throw new PostAdapterException(String.format("An Error occurred when calling the API [%s] | Error [%s]", postProperties.getUrlGetAll(), exception.getMessage()));
        }
    }

}
