package com.shivraj.medassist.Dto;


import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
    private long id;
    private String username;
    private String password;
    private String email;
    private String role;

}
