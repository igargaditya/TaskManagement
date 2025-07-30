package com.aditya.taskManager.service;

import com.aditya.taskManager.entity.Task;
import com.aditya.taskManager.enums.TaskStatus;
import com.aditya.taskManager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public void saveTask(Task newTask) {
        taskRepository.save(newTask);
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }


    public List<Task> findByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    public List<Task> findByAssignedTo(Long userId) {
        return taskRepository.findByAssignedTo_Id(userId);
    }

    public List<Task> findByStatusAndAssignedTo(TaskStatus status, Long userId) {
        return taskRepository.findByStatusAndAssignedTo_Id(status,userId);
    }



    public List<Task> getAll(Sort sort) {
        return taskRepository.findAll(sort);
    }
}
