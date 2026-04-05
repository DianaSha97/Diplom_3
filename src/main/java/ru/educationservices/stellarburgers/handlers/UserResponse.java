package ru.educationservices.stellarburgers.handlers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {

    private boolean success;
    private User user;
    private String accessToken;
    private String refreshToken;
    private String message;
}
