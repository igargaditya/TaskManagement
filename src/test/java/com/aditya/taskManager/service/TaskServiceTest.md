## Test Cases Documentation

This document lists all the unit tests written for the Task Management application using JUnit 5 and Mockito.
Each test covers one or more methods from the service layer (TaskService).

1. getAllTasks()
   - Test Name - testGetAll 
   - Purpose -  Fetch all tasks from the database.  

2. saveTask(Task newTask)
   - Test Name: testSaveTask
   - Purpose: Verify that the save() method is called with the correct task object.

3. findTaskById(Long id)
   - Test Name : testFindById_IfExists 
   - Purpose : Find a task by its ID.

   - Test Name : testFindById_IfNotExist
   - Purpose : Handle case where task is not found.  

4. deleteTaskById(Long id)
   - Test Name : testDeleteTaskById 
   - Purpose : Delete task by ID.

5. editTask(Task t1, Task t2) 
   - Test Name : testEditTask_ValidUpdate
   - Purpose : Find a task by its ID.

   - Test Name : testEditTask_InvalidUpdate
   - Purpose : Handle case where task is not found.

6. findByStatus(TaskStatus status)
   - Test Name : testFindByStatus
   - Purpose : Get tasks by status (Pending, In_Progress, Completed)

7. findByAssignedTo(Long userId)
   - Test Name : testFindByAssignedTo_shouldReturnTasksForUser
   - Purpose : Get all tasks assigned to a specific user.  

8. testFindByStatusAndAssignedTo(TaskStatus status, Long userId)

   - Test Name : testFindByStatusAndAssignedTo_shouldReturnFilteredTasks 
   - Purpose : Filter tasks based on both status and user.

9. testGetAll_withSort(Sort sort)
   - Test Name: testGetAll_withSort_shouldReturnSortedTasks
   - Purpose : Fetch all tasks sorted by a given field.  
   - Sort Used: Sort.by("createdAt")

