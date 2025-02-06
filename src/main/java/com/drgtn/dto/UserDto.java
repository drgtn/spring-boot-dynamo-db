package com.drgtn.dto;

import com.drgtn.model.Gender;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String email;
    private String password;
    private int age;
    private Gender gender;
}
