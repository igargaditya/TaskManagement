package com.aditya.taskManager.service;

import com.aditya.taskManager.entity.Task;
import com.aditya.taskManager.entity.User;
import com.aditya.taskManager.enums.TaskStatus;
import com.aditya.taskManager.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
        Task task1 = new Task(id,title);

        List<Task> mockTasks = List.of(task1);
        when(taskRepository.findAll()).thenReturn(mockTasks);
        List<Task> result = taskService.getAllTasks();
        assertEquals(1, result.size());
        assertEquals(title, result.get(0).getTitle());
        verify(taskRepository, times(1)).findAll();
    }

    @ParameterizedTest
    @CsvSource({
            "1,Task1","2,Task2","3,Task3"
    })
    void testSaveTask(Long Id,String title) {
        Task task = new Task(Id,title);

        taskService.saveTask(task);
        verify(taskRepository, times(1)).save(task);
    }


    @ParameterizedTest
    @CsvSource({
            "1,Task1","2,Task2","3,Task3"
    })
    void testFindById_IfExists(Long Id,String title) {
        Task task = new Task(Id,title);

        when(taskRepository.findById(Id)).thenReturn(Optional.of(task));
        Optional<Task> result = taskService.findTaskById(Id);
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
        Optional<Task> result = taskService.findTaskById(Id);
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
            "Old Title,Old Description,Pending,1,New Title,New Description," +
                    "Completed,2",

    })
    public void testEditTask_ValidUpdate(String oldTitle,String oldDescription,
                                         TaskStatus oldStatus,Long oldId,
                                         String newTitle,String newDescription,
                                         TaskStatus newStatus, Long newId) {

        User userOld = new User(oldId,"John");
        User userNew = new User(newId,"Doe");

        Task task = new Task(1L,oldTitle,oldDescription,oldStatus,userOld);
        task.setTitle(newTitle);
        task.setDescription(newDescription);
        task.setStatus(newStatus);
        task.setAssignedTo(userNew);


        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);


        taskService.editTask(task, task);

        Assertions.assertEquals("New Title", task.getTitle());
        Assertions.assertEquals("New Description", task.getDescription());
        Assertions.assertEquals(TaskStatus.Completed, task.getStatus());
        Assertions.assertEquals(userNew, task.getAssignedTo());


        Mockito.verify(taskRepository, Mockito.times(1)).save(task);
    }


    @ParameterizedTest
    @CsvSource({
            "Old Title,Old Description,Pending,1,New Title,New Description," +
                    "Completed,2",

    })
    public void testEditTask_InValidUpdate(String oldTitle,String oldDescription,
                                         TaskStatus oldStatus,Long oldId,
                                         String newTitle,String newDescription,
                                         TaskStatus newStatus, Long newId) {

        User userOld = new User(oldId,"John");
        User userNew = new User(newId,"Doe");

        Task task = new Task(1L,oldTitle,oldDescription,oldStatus,userOld);
        task.setTitle(newTitle);
        task.setDescription(newDescription);
        task.setStatus(newStatus);
        task.setAssignedTo(userNew);


        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);


        taskService.editTask(task, null);

        Assertions.assertEquals("New Title", task.getTitle());
        Assertions.assertEquals("New Description", task.getDescription());
        Assertions.assertEquals(TaskStatus.Completed, task.getStatus());
        Assertions.assertEquals(userNew, task.getAssignedTo());


        Mockito.verify(taskRepository, Mockito.times(1)).save(task);
    }


    @ParameterizedTest
    @CsvSource({
            "Pending","Completed","In_Progress"
    })
    public void testFindByStatus(TaskStatus expected){
        Task Task = new Task(1L,expected+" Task",expected);

        when(taskRepository.findByStatus(expected)).thenReturn(List.of(Task));
        assertEquals(1, taskService.findByStatus(expected).size());
        assertEquals(expected, taskService.findByStatus(expected).get(0).getStatus());
    }


    @ParameterizedTest
    @CsvSource({
            "1","2","3"
    })
    void testFindByAssignedTo(Long userId) {
        User user = new User(userId,"John");

        Task task = new Task(1L,"Task",user);

        List<Task> mockTasks = List.of(task);

        when(taskRepository.findByAssignedTo_Id(userId)).thenReturn(mockTasks);
        List<Task> result = taskService.findByAssignedTo(userId);
        assertEquals(1, result.size());
        assertEquals("Task", result.get(0).getTitle());
        assertEquals(userId, result.get(0).getAssignedTo().getId());
        verify(taskRepository, times(1)).findByAssignedTo_Id(userId);
    }


    @ParameterizedTest
    @CsvSource({
            "1,Pending","2,Completed","3,In_Progress"
    })
    void testFindByStatusAndAssignedTo(Long id,TaskStatus status) {
        User user = new User(id,"A");

        Task task = new Task(1L,status+" Task",status,user);

        List<Task> mockTasks = List.of(task);
        when(taskRepository.findByStatusAndAssignedTo_Id(status, id)).thenReturn(mockTasks);
        List<Task> result = taskService.findByStatusAndAssignedTo(status, id);
        assertEquals(1, result.size());
        assertEquals(status+" Task", result.get(0).getTitle());
        assertEquals(status, result.get(0).getStatus());
        assertEquals(id, result.get(0).getAssignedTo().getId());
        verify(taskRepository, times(1)).findByStatusAndAssignedTo_Id(status, id);
    }

    @Test
    void testGetAll_withSort() {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");

        Task task1 = new Task(1L,"Task A");

        Task task2 = new Task(2L,"Task B");

        List<Task> mockTasks = List.of(task1, task2);

        when(taskRepository.findAll(sort)).thenReturn(mockTasks);
        List<Task> result = taskService.getAllTasks(sort);
        assertEquals(2, result.size());
        assertEquals("Task A", result.get(0).getTitle());
        assertEquals("Task B", result.get(1).getTitle());
        verify(taskRepository, times(1)).findAll(sort);
    }
}


