package com.azure.monitoring.infrastructure.adapters.webadapter;

import com.azure.monitoring.domain.Post;
import com.azure.monitoring.model.UserPost;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    List<UserPost> map(List<Post> post);
}
