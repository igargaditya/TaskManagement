## Test Cases Documentation

This document lists all the unit tests written for the Task Management application using JUnit 5 and Mockito.
Each test covers one or more methods from the service layer (UserService).

1. getAllUsers()
    - Test Name - testGetAll
    - Purpose -  Fetch all users from the database.

2. saveUser(User newUser)
    - Test Name: testSaveUser
    - Purpose: Verify that the save() method is called with the correct user object.

3. findUserById(Long id)
    - Test Name : testFindById_IfExists
    - Purpose : Find a user by its ID.

    - Test Name : testFindById_IfNotExist
    - Purpose : Handle case where user is not found.

4. deleteUser(Long id)
    - Test Name : testDeleteUserById
    - Purpose : Delete user by ID.

5. editUser(User u1, User u2)
    - Test Name : testEditUser_ValidUpdate
    - Purpose : Find a user by its ID.

    - Test Name : testEditUser_InValidUpdate
    - Purpose : Handle case where user is not found.