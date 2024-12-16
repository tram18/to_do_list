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
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <h1 class="title">To-Do List</h1>
        <div class="form-container">
            <form action="" method="post">
                <input type="text" name="item" id="item" placeholder="Add a new task..." required>
                <button type="submit">Add Task</button>
            </form>
        </div>
        <ul class="task-list">
        	<c:forEach items="${list}" var="item">
        	<li><c:out value="${item}"/></li>
        	</c:forEach>
        </ul>
    </div>
</body>
</html>
