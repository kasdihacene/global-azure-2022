package com.azure.monitoring.infrastructure.apiadapter.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;


@Data
@Setter(AccessLevel.NONE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@JsonDeserialize(builder = PostResponse.PostResponseBuilder.class)
public class PostResponse {

    @JsonProperty("userId")
    Integer userId;
    @JsonProperty("id")
    Integer id;
    @JsonProperty("title")
    String title;
    @JsonProperty("body")
    String body;
}
