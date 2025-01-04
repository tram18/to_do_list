<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>To-Do List</title>
  <link rel="stylesheet"  type="text/css" href="style1.css">
  <script src="main.js"></script>
</head>

<body>
  <div class="container">
    <h1 class="title">To-Do List</h1>

    <!-- Add New Task List Form -->
    <div class="form-container">
      <form action="" method="post">
        <input type="hidden" name="action" value="addList" />
        <input type="text" name="listName" placeholder="Add a new list..." required>
        <button type="submit">Add List</button>
      </form>
    </div>

    <!-- Display Task Lists and Items -->
    <div class="task-list-container">
      <c:forEach items="${taskLists}" var="taskList">
        <div id="task-list-${taskList.id}" class="task-list">

          <div id="warning-${taskList.id}" class="warning-box" style="visibility: hidden;">
            The list has incomplete items. Are you sure you want to delete it?
            <button class="warning-delete-button" onclick="deleteTaskList(${taskList.id})">Delete</button>
            <button class="warning-cancel-button" onclick="cancelDelete(${taskList.id})">Cancel</button>
          </div>

          <div class="task-list-header">
            <c:out value="${taskList.listName}" />

            <!---------Updating ListName----------->
           <button id="update-list-name-${taskList.id}" class="update-list-name" onclick="showListUpdate(${taskList.id})">
             &#10000;</button>

           <div id="update-form-container-${taskList.id}" class="update-form-container" style="display: none;">
             <form id="update-form-${taskList.id}" class="update-form" method="POST" action="">
               <input type="hidden" name="action" value="updateList" />
               <input type="hidden" name="listId" value="${taskList.id}" />
               <input type="text" name="listName" placeholder="Enter new list name" required />
               <button type="submit">Update</button>
               <button type="button" onclick="hideListUpdate(${taskList.id})">Cancel</button>
             </form>
           </div>



              <!------Delete List Name ------->
            <form id="delete-form-${taskList.id}"
                    class="delete-form" style="display: inline;">

                <input type="hidden" name="action" value="deleteList" />
                <input type="hidden" name="listId" value="${taskList.id}" />

            </form>
            <button class="delete-button" onclick="confirmDelete(${taskList.id})">
                &times;
              </button>

          </div>


          <c:forEach items="${taskList.taskItems}" var="item">
            <div class="task-item">

              <!-- Check box for items completed-->
              <form action="" method=post class="task-form">
                <input type="hidden" name="action" value="addCompletedItem" />
                <input type="hidden" name="taskId" value="${item.id}" />
                <input type="checkbox" name="completed" value="true" ${item.completed ? 'checked' : '' } onchange="this.form.submit();" />
                <span class="task-item-text ${item.completed ? 'completed' : ''}">${item.taskName}</span>
              </form>


              <!-- Delete items button -->
              <form action="" method="post" class="delete-form" style="display: inline;">
                <input type="hidden" name="action" value="deleteItem" />
                <input type="hidden" name="itemId" value="${item.id}" />
                <button type="submit" class="delete-button">
                  &times;
                </button>
              </form>

            </div>
          </c:forEach>
        </div>
      </c:forEach>
    </div>

    <!-- Add New Task Item Form -->
    <div class="form-container">
      <form action="" method="post">
        <input type="hidden" name="action" value="addItem" />
        <select name="taskListId" required>
          <option value="" disabled selected>Select a list</option>
          <c:forEach items="${taskLists}" var="taskList">
            <option value="${taskList.id}">${taskList.listName}</option>
          </c:forEach>
        </select>
        <input type="text" name="itemName" placeholder="Add a new task..." required>
        <button type="submit">Add Task</button>
      </form>
    </div>
  </div>
</body>

</html>