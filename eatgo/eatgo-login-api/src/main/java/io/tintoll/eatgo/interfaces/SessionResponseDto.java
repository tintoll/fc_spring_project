package io.tintoll.eatgo.interfaces;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionResponseDto {
    private String accessToken;
}
