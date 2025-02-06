package com.drgtn.fixtures;

import com.drgtn.dto.UserDto;
import com.drgtn.model.Gender;

public class UserDtoFixture {

    private UserDtoFixture() {
    }

    public static UserDto aUserDto() {
        return UserDto.builder()
                .email("abc@gmail.com")
                .age(25)
                .gender(Gender.MALE)
                .password("strongPassword")
                .build();
    }
}
