package com.project.dlvery.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.project.dlvery.entity.User;
import com.project.dlvery.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void whenSaveUser_shouldReturnUser() {
        User user = new User();
        user.setUsername("Test");

        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);

        User created = userService.createUser(user);

        assertThat(created.getUsername()).isSameAs(user.getUsername());
        verify(userRepository).save(user);
    }
    


    @Test
    public void whenGivenUsername_shouldDeleteUser_ifFound(){
        User user = new User();
        user.setUsername("Test");

        lenient().when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        userService.deleteByUsername(user.getUsername());
        verify(userRepository).deleteByUsername(user.getUsername());
    }

    @Test
    public void when_user_doesnt_exist() {
        User user = new User();
        user.setUsername("Test Name");

        lenient().when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(null));
        userService.deleteByUsername(user.getUsername());
    }
    

    @Test
    public void whenGivenId_shouldReturnUser_ifFound() {
        User user = new User();
        user.setId(5);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User expected = userService.findById(user.getId()).get();

        assertThat(expected).isSameAs(user);
        verify(userRepository).findById(user.getId());
    }

    @Test
    public void should_throw_exception_when_user_doesnt_exist() {
        User user = new User();
        user.setId(1);

        lenient().when(userRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        userService.findById(user.getId());
    }
    
    @Test
    public void shouldReturnAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User());

        when(userRepository.findAll()).thenReturn(users);

        List<User> expected = userService.listUsers();

        assertEquals(expected, users);
        verify(userRepository).findAll();
    }
    
    @Test
    public void whenGivenId_shouldUpdateUser_ifFound() {
        User user = new User();
        user.setId(13);
        user.setFirstName("Test");

        User newUser = new User();
        user.setFirstName("New Test");

        lenient().when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.updateUser(newUser);

        verify(userRepository).save(newUser);
        //verify(userRepository).findById(user.getId());
    }

    
}
