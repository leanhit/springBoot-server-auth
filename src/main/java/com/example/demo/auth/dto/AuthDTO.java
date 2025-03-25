package com.example.demo.auth.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthDTO {
    private String username;
    private String email;
}
