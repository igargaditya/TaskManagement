package com.aditya.taskManager.controller;
import com.aditya.taskManager.entity.User;
import com.aditya.taskManager.exception.InvalidTaskException;
import com.aditya.taskManager.exception.ResourceNotFoundException;
import com.aditya.taskManager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public List<User> getAllUsers(){
        List<User> list = userService.getAllUsers();
        if (list == null || list.isEmpty()) {
            throw new ResourceNotFoundException("No Users Found");
        }
        return list;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(@RequestBody @Valid User newUser){
        try{
            userService.saveUser(newUser);
            return "User Created";
        } catch (Exception e) {
            throw new InvalidTaskException("User Body is Incorrect");
        }
    }

    @GetMapping("/{id}")
    public User getTaskById(@PathVariable Long id){
        User user = userService.findUserById(id).orElse(null);
        if(user!=null){
            return user ;
        }
        throw new ResourceNotFoundException("No User With Given ID");
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteUser(@PathVariable Long id){
        User user = userService.findUserById(id).orElse(null);
        if(user!=null){
            userService.deleteUser(id);
            return "User Deleted";
        }
        throw new ResourceNotFoundException("No Task with Given ID");
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public String deleteUser(@PathVariable Long id,@RequestBody @Valid User newUser){
        User user = userService.findUserById(id).orElse(null);
        if (user!=null) {
            userService.editTask(user, newUser);
            return "Task Updated";
        }
        throw new ResourceNotFoundException("No Task with Given ID");
    }

}
