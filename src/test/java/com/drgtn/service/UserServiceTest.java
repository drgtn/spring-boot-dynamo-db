package com.drgtn.service;

import com.drgtn.dto.UserDto;
import com.drgtn.exception.UserAlreadyExistsException;
import com.drgtn.exception.UserNotFoundException;
import com.drgtn.mapper.UserMapper;
import com.drgtn.model.User;
import com.drgtn.repository.UserRepository;
import com.drgtn.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.drgtn.fixtures.UserDtoFixture.aUserDto;
import static com.drgtn.fixtures.UserFixture.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder bCryptPasswordEncoder;
    @Mock
    private UserMapper userMapper;

    @Test
    void testRegister() {
        UserDto aUserDto = aUserDto();
        User aUser = aUser();
        when(userRepository.findByEmail(aUserDto.getEmail())).thenReturn(Optional.empty());
        when(bCryptPasswordEncoder.encode(aUserDto.getPassword())).thenReturn("hashedStrongPassword");
        when(userMapper.map(aUserDto, "hashedStrongPassword")).thenReturn(aUser);
        userService.register(aUserDto);
        verify(userRepository).save(aUser);
    }

    @Test
    void testRegisterEmailExists() {
        UserDto aUserDto = aUserDto();
        User aUser = aUser();
        when(userRepository.findByEmail(aUserDto.getEmail())).thenReturn(Optional.of(aUser));
        assertThat(catchThrowable(() -> userService.register(aUserDto))).isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining("Email already exists!");
        verify(userRepository, never()).save(aUser);
    }

    @Test
    void testLogin() {
        UserDto aUserDto = aUserDto();
        User aUser = aUser();
        when(userRepository.findByEmail(aUserDto.getEmail())).thenReturn(Optional.of(aUser));
        when(bCryptPasswordEncoder.matches(aUser.getPassword(), aUser.getPassword())).thenReturn(true);
        assertThat(userService.login(aUserDto)).isNotBlank();
    }

    @Test
    void testLoginUserDoesntExists() {
        UserDto aUserDto = aUserDto();
        when(userRepository.findByEmail(aUserDto.getEmail())).thenReturn(Optional.empty());
        assertThat(catchThrowable(() -> userService.login(aUserDto)))
                .isInstanceOf(UserNotFoundException.class).hasMessageContaining("Invalid email or password");
    }

    @Test
    void testLoginUserExistsWrongPassword() {
        UserDto aUserDto = aUserDto();
        User aUser = aUser();
        when(userRepository.findByEmail(aUserDto.getEmail())).thenReturn(Optional.of(aUser));
        when(bCryptPasswordEncoder.matches(aUser.getPassword(), aUser.getPassword())).thenReturn(false);
        assertThat(catchThrowable(() -> userService.login(aUserDto)))
                .isInstanceOf(UserNotFoundException.class).hasMessageContaining("Invalid email or password");
    }
}
