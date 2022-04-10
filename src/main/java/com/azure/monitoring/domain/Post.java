package com.azure.monitoring.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Post {

    private Integer userId;
    private Integer id;
    private String title;
    private String body;

}
