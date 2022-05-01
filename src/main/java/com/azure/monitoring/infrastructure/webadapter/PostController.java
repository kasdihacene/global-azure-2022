package com.azure.monitoring.infrastructure.webadapter;

import com.azure.monitoring.api.V1Api;
import com.azure.monitoring.application.port.in.RetrievePostsUseCase;
import com.azure.monitoring.model.UserPost;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public record PostController(RetrievePostsUseCase postsUseCase, PostMapper postMapper)
        implements V1Api {

    @SneakyThrows
    @Operation(summary = "Get the published posts", description = "Fetch some published posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched")})
    @Override
    public ResponseEntity<List<UserPost>> getAllPosts(@ApiParam(value = "Number of tweets to retrieve", required = true) @PathVariable("numberOfTweets") Integer numberOfTweets) {
        log.info("Calling the Tweeter restController to get [{}] tweets.", numberOfTweets);

        if (numberOfTweets > 20) Thread.sleep(5000);

        List<UserPost> posts = postMapper.map(postsUseCase.fetchAllPosts(numberOfTweets));
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @Operation(summary = "Generate 5xx error", description = "Get some error")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched"),
            @ApiResponse(responseCode = "500", description = "Unexpected error")
    })
    @Override
    public ResponseEntity<Object> generateError() {
        // Generate an exception
        Integer.valueOf("SOME_STRING");
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
