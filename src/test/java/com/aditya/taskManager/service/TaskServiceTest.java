package com.aditya.taskManager.service;

import com.aditya.taskManager.entity.Task;
import com.aditya.taskManager.entity.User;
import com.aditya.taskManager.enums.TaskStatus;
import com.aditya.taskManager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @ParameterizedTest
    @CsvSource({
            "1,Task1","2,Task2","3,Task3"
    })
    void testGetAll(Long id,String title) {
        Task task1 = new Task();
        task1.setId(id);
        task1.setTitle(title);
        List<Task> mockTasks = List.of(task1);
        when(taskRepository.findAll()).thenReturn(mockTasks);
        List<Task> result = taskService.getAll();
        assertEquals(1, result.size());
        assertEquals(title, result.get(0).getTitle());
        verify(taskRepository, times(1)).findAll();
    }

    @ParameterizedTest
    @CsvSource({
            "1,Task1","2,Task2","3,Task3"
    })
    void testSaveTask(Long userId,String title) {
        Task task = new Task();
        task.setId(userId);
        task.setTitle(title);
        taskService.saveTask(task);
        verify(taskRepository, times(1)).save(task);
    }


    @ParameterizedTest
    @CsvSource({
            "1,Task1","2,Task2","3,Task3"
    })
    void testFindById_IfExists(Long Id,String title) {
        Task task = new Task();
        task.setId(Id);
        task.setTitle(title);
        when(taskRepository.findById(Id)).thenReturn(Optional.of(task));
        Optional<Task> result = taskService.findById(Id);
        assertTrue(result.isPresent());
        assertEquals(title, result.get().getTitle());
        assertEquals(Id, result.get().getId());
        verify(taskRepository, times(1)).findById(Id);
    }

    @ParameterizedTest
    @CsvSource({
            "1","2","3"
    })
    void testFindById_IfNotExist(Long Id) {
        when(taskRepository.findById(Id)).thenReturn(Optional.empty());
        Optional<Task> result = taskService.findById(Id);
        assertFalse(result.isPresent());
        verify(taskRepository, times(1)).findById(Id);
    }

    @ParameterizedTest
    @CsvSource({
            "1","2","3"
    })
    void testDeleteTaskById(Long Id) {
        taskService.deleteTaskById(Id);
        verify(taskRepository, times(1)).deleteById(Id);
    }


    @ParameterizedTest
    @CsvSource({
            "Pending","Completed","In_Progress"
    })
    public void testFindByStatus(TaskStatus expected){
        Task Task = new Task();
        Task.setId(1L);
        Task.setTitle(expected +"Task");
        Task.setStatus(expected);

        when(taskRepository.findByStatus(expected)).thenReturn(List.of(Task));
        assertEquals(1, taskService.findByStatus(expected).size());
        assertEquals(expected, taskService.findByStatus(expected).get(0).getStatus());
    }


    @ParameterizedTest
    @CsvSource({
            "1","2","3"
    })
    void testFindByAssignedTo(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setFirstName("John");

        Task task = new Task();
        task.setId(101L);
        task.setTitle("Single Task");
        task.setAssignedTo(user);

        List<Task> mockTasks = List.of(task);

        when(taskRepository.findByAssignedTo_Id(userId)).thenReturn(mockTasks);
        List<Task> result = taskService.findByAssignedTo(userId);
        assertEquals(1, result.size());
        assertEquals("Single Task", result.get(0).getTitle());
        assertEquals(userId, result.get(0).getAssignedTo().getId());
        verify(taskRepository, times(1)).findByAssignedTo_Id(userId);
    }


    @ParameterizedTest
    @CsvSource({
            "1,Pending","2,Completed","3,In_Progress"
    })
    void testFindByStatusAndAssignedTo(Long Id,TaskStatus status) {
        User user = new User();
        user.setId(Id);
        user.setFirstName("A");
        Task task = new Task();
        task.setId(101L);
        task.setTitle(status+" Task");
        task.setStatus(status);
        task.setAssignedTo(user);
        List<Task> mockTasks = List.of(task);
        when(taskRepository.findByStatusAndAssignedTo_Id(status, Id)).thenReturn(mockTasks);
        List<Task> result = taskService.findByStatusAndAssignedTo(status, Id);
        assertEquals(1, result.size());
        assertEquals(status+" Task", result.get(0).getTitle());
        assertEquals(status, result.get(0).getStatus());
        assertEquals(Id, result.get(0).getAssignedTo().getId());
        verify(taskRepository, times(1)).findByStatusAndAssignedTo_Id(status, Id);
    }

    @Test
    void testGetAll_withSort() {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");

        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task A");

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task B");

        List<Task> mockTasks = List.of(task1, task2);

        when(taskRepository.findAll(sort)).thenReturn(mockTasks);
        List<Task> result = taskService.getAll(sort);
        assertEquals(2, result.size());
        assertEquals("Task A", result.get(0).getTitle());
        assertEquals("Task B", result.get(1).getTitle());
        verify(taskRepository, times(1)).findAll(sort);
    }
}


