package com.drgtn.service.impl;

import com.drgtn.dto.UserDto;
import com.drgtn.exception.UserAlreadyExistsException;
import com.drgtn.exception.UserNotFoundException;
import com.drgtn.mapper.UserMapper;
import com.drgtn.model.User;
import com.drgtn.repository.UserRepository;
import com.drgtn.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    @Override
    public void register(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists!");
        }
        userRepository.save(userMapper.map(userDto, bCryptPasswordEncoder.encode(userDto.getPassword())));
    }

    @Override
    public String login(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail()).orElseThrow(() -> new UserNotFoundException("Invalid email or password"));
        if (!bCryptPasswordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid email or password!");
        }
        return user.getUserId();
    }
}
