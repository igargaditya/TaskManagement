package com.aditya.taskManager.controller;
import com.aditya.taskManager.entity.User;
import com.aditya.taskManager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User newUser){
        try{
            userService.saveUser(newUser);
            return new ResponseEntity<>(newUser,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllTasks(){
        List<User> list = userService.getAll();
        if(list!=null && !list.isEmpty()){
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){

        try{
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id,@RequestBody @Valid User newUser){
            User user = userService.editUser(id,newUser);
            if(user!=null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }

}
