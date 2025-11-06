package com.ecommerce.midchallenge.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String token;
    
    @Builder.Default
    private String type = "Bearer";
}


























