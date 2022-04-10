package com.azure.monitoring.infrastructure.webadapter;

import com.azure.monitoring.api.V1Api;
import com.azure.monitoring.application.port.in.RetrievePostsUseCase;
import com.azure.monitoring.model.UserPost;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public record PostController(RetrievePostsUseCase postsUseCase, PostMapper postMapper)
        implements V1Api {

    @Operation(summary = "Get the published posts", description = "Fetch all published posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched")})
    @Override
    public ResponseEntity<List<UserPost>> getAllPosts() {

        List<UserPost> posts = postMapper.map(postsUseCase.fetchAllPosts());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10000; i++) {
            log.info("Running and printing from the service {}", i);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
