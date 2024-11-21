package com.neo.client.project.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class LoginPostRequest {
    @Schema(example = "(\\\"{\"userName\":\"USERNAME\",\"password\":\"12345\")")
    String loginRequest;
}
