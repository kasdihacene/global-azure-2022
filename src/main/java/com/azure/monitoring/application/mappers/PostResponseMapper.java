package com.azure.monitoring.application.mappers;

import com.azure.monitoring.domain.Post;
import com.azure.monitoring.infrastructure.adapters.apiadapter.post.PostResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostResponseMapper {

    List<Post> map(List<PostResponse> postResponses);
}
