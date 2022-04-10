package com.azure.monitoring.infrastructure.webadapter;

import com.azure.monitoring.application.port.in.RetrievePostsUseCase;
import com.azure.monitoring.domain.Post;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PostController.class, RetrievePostsUseCase.class, PostMapperImpl.class})
@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RetrievePostsUseCase postService;

    @Test
    void should_get_all_posts() throws Exception {
        // Arrange
        Post postFactory = Post.builder()
                .id(1)
                .userId(1)
                .title("Fake news")
                .body("Coronavirus Bioweapon – How China Stole Coronavirus From Canada And Weaponized It")
                .build();
        List<Post> postCollection = Collections.singletonList(postFactory);
        Mockito.when(postService.fetchAllPosts()).thenReturn(postCollection);

        // Act
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/tweets"))
        // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].userId").value(1))
                .andExpect(jsonPath("$.[0].title").value("Fake news"))
                .andExpect(jsonPath("$.[0].body").value("Coronavirus Bioweapon – How China Stole Coronavirus From Canada And Weaponized It"))
        ;
    }
}
