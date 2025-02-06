package com.drgtn.fixtures;

import com.drgtn.model.Gender;
import com.drgtn.model.User;

import java.util.UUID;

public class UserFixture {
    private UserFixture() {
    }

    public static User aUser() {
        return User.builder()
                .userId(UUID.randomUUID().toString())
                .email("abc@gmail.com")
                .age(25)
                .gender(Gender.MALE)
                .password("strongPassword")
                .build();
    }
}
