## Test Cases Documentation

This document lists all the unit tests written for the Task Management application using JUnit 5 and Mockito.
Each test covers one or more methods from the service layer (TaskService).

1. getAll(Long id,String title)
   - Test Name - testGetAll 
   - Purpose -  Fetch all tasks from the database.  

2. testSaveTask(Long UserId,String title)
   - Test Name: testSaveTask
   - Purpose: Verify that the save() method is called with the correct task object.

3. findById(Long id)
   - Test Name : testFindById_IfExists 
   - Purpose : Find a task by its ID.

   - Test Name : testFindById_IfNotExist
   - Purpose : Handle case where task is not found.  

4. deleteTaskById(Long id)
   - Test Name : testDeleteTaskById 
   - Purpose : Delete task by ID.

5. findByStatus(TaskStatus status)
   - Test Name : testFindByStatus
   - Purpose : Get tasks by status (Pending, In_Progress, Completed)

6. testFindByAssignedTo(Long userId)
   - Test Name : testFindByAssignedTo_shouldReturnTasksForUser
   - Purpose : Get all tasks assigned to a specific user.  

7. testFindByStatusAndAssignedTo(TaskStatus status, Long userId)

   - Test Name : testFindByStatusAndAssignedTo_shouldReturnFilteredTasks 
   - Purpose : Filter tasks based on both status and user.

8. testGetAll_withSort(Sort sort)
   - Test Name: testGetAll_withSort_shouldReturnSortedTasks
   - Purpose : Fetch all tasks sorted by a given field.  
   - Sort Used: Sort.by("createdAt")

