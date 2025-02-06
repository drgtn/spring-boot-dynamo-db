package com.drgtn.service;

import com.drgtn.dto.UserDto;

public interface UserService {
    void register(UserDto userDto);

    String login(UserDto userDto);
}
