package com.aditya.taskManager.service;

import com.aditya.taskManager.entity.User;
import com.aditya.taskManager.exception.InvalidTaskException;
import com.aditya.taskManager.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void saveUser(User newUser) {
        userRepository.save(newUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public void editTask(User user, @Valid User newUser) {
        try {
            user.setFirstName(newUser.getFirstName());
            user.setLastName(newUser.getLastName());
            user.setIsActive(newUser.getIsActive());
            user.setTimeZone(newUser.getTimeZone());
            saveUser(user);
        }
        catch(Exception e){
            throw new InvalidTaskException("Task Body is Incorrect");
        }

    }
}
