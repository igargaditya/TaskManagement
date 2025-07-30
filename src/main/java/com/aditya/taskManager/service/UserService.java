package com.aditya.taskManager.service;

import com.aditya.taskManager.entity.User;
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

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public Optional<User> findUserById(Long id){
        return userRepository.findById(id);
    }

    public User editUser(Long id, @Valid User newUser) {
        User oldUser = findUserById(id).orElse(null);
        if(oldUser!=null){
            oldUser.setFirstName(newUser.getFirstName());
            oldUser.setLastName(newUser.getLastName());
            oldUser.setTimeZone(newUser.getTimeZone());
            oldUser.setActive(newUser.isActive());
            saveUser(oldUser);
            return oldUser;
        }
        return null ;

    }
}
