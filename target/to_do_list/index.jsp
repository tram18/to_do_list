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
    <link rel="stylesheet" href="style1.css">
</head>
<body>
    <div class="container">
           <h1 class="title">To-Do List</h1>

           <!-- Add New Task List Form -->
           <div class="form-container">
               <form action="" method="post">
                   <input type="text" name="listName" placeholder="Add a new list..." required>
                   <button type="submit">Add List</button>
               </form>
           </div>

           <!-- Display Task Lists and Items -->
           <div class="task-list-container">
               <c:forEach items="${taskLists}" var="taskList">
                   <div class="task-list">
                       <div class="task-list-header"><c:out value="${taskList.listName}" /></div>

                       <c:forEach items="${taskList.taskItems}" var="item">
                           <div class="task-item">
                               <span class="task-item-text">${item.taskName}</span>
                           </div>
                       </c:forEach>
                   </div>
               </c:forEach>
           </div>

           <!-- Add New Task Item Form -->
           <div class="form-container">
               <form action="" method="post">
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
