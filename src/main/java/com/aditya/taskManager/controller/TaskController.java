package com.aditya.taskManager.controller;

import com.aditya.taskManager.entity.Task;
import com.aditya.taskManager.enums.TaskStatus;
import com.aditya.taskManager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(){
        List<Task> list = taskService.getAll();
        if(list!=null && !list.isEmpty()){
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody @Valid Task newTask){
        try{
            taskService.saveTask(newTask);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        Task taskById = taskService.findById(id).orElse(null);
        if(taskById!=null){
            return new ResponseEntity<>(taskById,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable Long id){
        Task taskById = taskService.findById(id).orElse(null);
        if(taskById!=null){
            taskService.deleteTaskById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTaskById(@PathVariable Long id, @RequestBody @Valid Task updateTask){
        Task taskById = taskService.findById(id).orElse(null);
        if(taskById!=null){
            taskById.setTitle(updateTask.getTitle());
            taskById.setDescription(updateTask.getDescription()!=null && !updateTask.getDescription().equals("")?
                    updateTask.getDescription() : taskById.getDescription());
            taskById.setStatus(updateTask.getStatus()!=null && !updateTask.getStatus().equals("")?
                    updateTask.getStatus() : taskById.getStatus());
            taskService.saveTask(taskById);
            return new ResponseEntity<>(taskById,HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/filter")
    public ResponseEntity<List<Task>> getFilteredTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) Long userId
    ) {
        List<Task> tasks;
        if (status != null && userId != null) {
            tasks = taskService.findByStatusAndAssignedTo(status, userId);
        } else if (status != null) {
            tasks = taskService.findByStatus(status);
        } else if (userId != null) {
            tasks = taskService.findByAssignedTo(userId);
        } else {
            tasks = taskService.getAll();
        }
        return new ResponseEntity<>(tasks,HttpStatus.OK);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Task>> getSortedTasks(
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        String[] sortFields = sortBy.split(",");

        Sort sort = Sort.by(
                Arrays.stream(sortFields)
                        .map(field -> direction.equalsIgnoreCase("desc") ? Sort.Order.desc(field) : Sort.Order.asc(field))
                        .toList()
        );

        List<Task> tasks = taskService.getAll(sort);
        return new ResponseEntity<>(tasks,HttpStatus.OK);
    }


}
