package com.azure.monitoring.infrastructure.webadapter;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PostIntegrationTest {

    private static final String LOCALHOST = "localhost";
    private static final int PORT = 7070;
    private static WireMockServer wireMockServer;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void init() {
        wireMockServer = new WireMockServer(
                new WireMockConfiguration().port(PORT)
        );
        wireMockServer.start();
        WireMock.configureFor(LOCALHOST, PORT);
    }

    @AfterAll
    static void reset() {
        wireMockServer.shutdown();
    }

    // https://wiremock.org/docs/stubbing/
    @Test
    void should_fetch_all_tweets_from_api() throws Exception {
        String expectedResponse = """
                [{
                "userId":1,
                "id":1,
                "title":"Fake news",
                "body":"Coronavirus Bioweapon – How China Stole Coronavirus From Canada And Weaponized It"
                }]
                """;
        stubFor(WireMock.get(urlMatching("/posts"))
                .willReturn(okJson(expectedResponse)
                        .withHeader("Content-Type", "application/json")));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/tweets/1"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].userId").value(1))
                .andExpect(jsonPath("$.[0].title").value("Fake news"))
                .andExpect(jsonPath("$.[0].body").value("Coronavirus Bioweapon – How China Stole Coronavirus From Canada And Weaponized It"));

        verify(getRequestedFor(urlPathEqualTo("/posts")));
    }

}
