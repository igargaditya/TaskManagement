package com.aditya.taskManager.repository;

import com.aditya.taskManager.entity.Task;
import com.aditya.taskManager.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);
    List<Task> findByAssignedTo_Id(Long userId);
    List<Task> findByStatusAndAssignedTo_Id(TaskStatus status, Long userId);
}
