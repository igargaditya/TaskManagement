package com.aditya.taskManager.service;
import com.aditya.taskManager.entity.Task;
import com.aditya.taskManager.entity.User;
import com.aditya.taskManager.enums.TaskStatus;
import com.aditya.taskManager.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @ParameterizedTest
    @CsvSource({
            "1,f,l,t,true"
    })
    void testSaveUser(Long userId,String firstName,String lastName,String timeZone,Boolean isActive) {
        User user = new User(userId,firstName,lastName,timeZone,isActive);

        userService.saveUser(user);
        verify(userRepository, times(1)).save(user);
    }


    @ParameterizedTest
    @CsvSource({
            "1,f,l,t,true"
    })
    void testGetAll(Long userId,String firstName,String lastName,String timeZone,Boolean isActive) {
        User user = new User(userId,firstName,lastName,timeZone,isActive);
        List<User> mockUsers = List.of(user);
        when(userRepository.findAll()).thenReturn(mockUsers);
        List<User> result = userService.getAllUsers();
        assertEquals(1, result.size());
        assertEquals(firstName, result.get(0).getFirstName());
        assertEquals(lastName, result.get(0).getLastName());
        assertEquals(timeZone, result.get(0).getTimeZone());
        assertEquals(isActive, result.get(0).getIsActive());

        verify(userRepository, times(1)).findAll();
    }


    @ParameterizedTest
    @CsvSource({
            "1","2","3"
    })
    void testDeleteUserById(Long Id) {
        userService.deleteUser(Id);
        verify(userRepository, times(1)).deleteById(Id);
    }


    @ParameterizedTest
    @CsvSource({
            "1","2","3"
    })
    void testFindById_IfExists(Long userId,String firstName,String lastName,String timeZone,Boolean isActive) {
        User user = new User(userId,firstName,lastName,timeZone,isActive);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Optional<User> result = userService.findUserById(userId);
        assertTrue(result.isPresent());
        assertEquals(firstName, result.get().getFirstName());
        assertEquals(userId, result.get().getId());
        verify(userRepository, times(1)).findById(userId);
    }

    @ParameterizedTest
    @CsvSource({
            "1","2","3"
    })
    void testFindById_IfNotExist(Long Id) {
        when(userRepository.findById(Id)).thenReturn(Optional.empty());
        Optional<User> result = userService.findUserById(Id);
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(Id);
    }


    @ParameterizedTest
    @CsvSource({
            "oldFirstName,oldLastName,Asia/Kolkata,true,newFirstName,newLastName,Europe/London,false"
    })
    public void testEditUser_ValidUpdate(String oldFirstName, String oldLastName,
                                         String oldTimeZone, Boolean oldIsActive,
                                         String newFirstName, String newLastName,
                                         String newTimeZone, Boolean newIsActive) {

        User userOld = new User(1L, oldFirstName, oldLastName, oldTimeZone, oldIsActive);
        User userNew = new User(2L, newFirstName, newLastName, newTimeZone, newIsActive);

        userService.editUser(userOld, userNew);


        Assertions.assertEquals(newFirstName, userOld.getFirstName());
        Assertions.assertEquals(newLastName, userOld.getLastName());
        Assertions.assertEquals(newTimeZone, userOld.getTimeZone());
        Assertions.assertEquals(newIsActive, userOld.getIsActive());

        Mockito.verify(userRepository, Mockito.times(1)).save(userOld);
    }


    @ParameterizedTest
    @CsvSource({
            "oldFirstName,oldLastName,Asia/Kolkata,true,newFirstName,newLastName,Europe/London,false"
    })
    public void testEditUser_InValidUpdate(String oldFirstName, String oldLastName,
                                         String oldTimeZone, Boolean oldIsActive,
                                         String newFirstName, String newLastName,
                                         String newTimeZone, Boolean newIsActive) {

        User userOld = new User(1L, oldFirstName, oldLastName, oldTimeZone, oldIsActive);

        userService.editUser(userOld, null);


        Assertions.assertEquals(newFirstName, userOld.getFirstName());
        Assertions.assertEquals(newLastName, userOld.getLastName());
        Assertions.assertEquals(newTimeZone, userOld.getTimeZone());
        Assertions.assertEquals(newIsActive, userOld.getIsActive());

        Mockito.verify(userRepository, Mockito.times(1)).save(userOld);
    }

}
