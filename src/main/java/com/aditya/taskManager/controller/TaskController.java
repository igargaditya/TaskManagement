package com.aditya.taskManager.controller;

import com.aditya.taskManager.entity.Task;
import com.aditya.taskManager.enums.TaskStatus;
import com.aditya.taskManager.exception.InvalidTaskException;
import com.aditya.taskManager.exception.ResourceNotFoundException;
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
    public List<Task> getAllTasks(){
        List<Task> list = taskService.getAllTasks();
        if (list == null || list.isEmpty()) {
            throw new ResourceNotFoundException("No Tasks Found");
        }
        return list;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createTask(@RequestBody @Valid Task newTask){
        try{
            taskService.saveTask(newTask);
            return "Task Created";
        }
        catch (Exception e) {
            throw new InvalidTaskException("Task Body is Incorrect");
        }
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id){
        Task task = taskService.findTaskById(id).orElse(null);
        if(task!=null){
            return task ;
        }
        throw new ResourceNotFoundException("No Task With Given ID");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteTaskById(@PathVariable Long id){
        Task task = taskService.findTaskById(id).orElse(null);
        if(task!=null){
            taskService.deleteTaskById(id);
            return "Task Deleted";
        }
        throw new ResourceNotFoundException("No Task with Given ID");
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public String updateTaskById(@PathVariable Long id, @RequestBody @Valid Task updateTask) {
            Task task = taskService.findTaskById(id).orElse(null);
            if (task!=null) {
                taskService.editTask(task, updateTask);
                return "Task Updated";
            }
           throw new ResourceNotFoundException("No Task with Given ID");
    }


    @GetMapping("/filter")
    public List<Task> getFilteredTasks(
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
            tasks = taskService.getAllTasks();
        }

        if(!tasks.isEmpty()) return tasks ;
        throw new ResourceNotFoundException("No Task Found");
    }

    @GetMapping("/sorted")
    public List<Task> getSortedTasks(
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        String[] sortFields = sortBy.split(",");

        Sort sort = Sort.by(
                Arrays.stream(sortFields)
                        .map(field -> direction.equalsIgnoreCase("desc") ? Sort.Order.desc(field) : Sort.Order.asc(field))
                        .toList()
        );

        List<Task> tasks = taskService.getAllTasks(sort);
        if(!tasks.isEmpty()) return tasks;
        throw new ResourceNotFoundException("No Task Found");
    }


}
