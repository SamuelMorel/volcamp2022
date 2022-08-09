package org.volcamp.api.utils.token;

import lombok.Builder;
import lombok.Data;

@Data
public class UserPasswordInput {
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String PASSWORD = "password";
    private String login;
    private String password;
    private String refreshToken;

    public TokenRequestDTO toTokenRequestDTO(String client_id, String client_secret, String app_name) {
        return TokenRequestDTO.builder()
                .username(login)
                .refresh_token(refreshToken)
                .password(password)
                .grant_type(refreshToken != null ? REFRESH_TOKEN : PASSWORD)
                .audience(app_name)
                .client_id(client_id)
                .client_secret(client_secret)
                .build();
    }

    @Builder
    @Data
    public static class TokenRequestDTO {
        private String grant_type;
        private String refresh_token;
        private String password;
        private String username;
        private String client_id;
        private String client_secret;
        private String audience;
    }

}
