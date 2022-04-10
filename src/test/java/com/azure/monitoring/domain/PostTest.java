package com.azure.monitoring.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PostTest {

    @Test
    void should_build_a_post_with_all_properties(){
        // Given
        Post expectedPost = somePost();

        // When
        Post actualPost = Post.builder().userId(1).id(1).body("some body").title("some title").build();

        // Then
        Assertions.assertThat(actualPost).isEqualTo(expectedPost);
    }

    private Post somePost() {
        return Post.builder().userId(1).id(1).body("some body").title("some title").build();
    }
}
