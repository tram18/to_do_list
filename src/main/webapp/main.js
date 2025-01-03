async function getData(taskListId) {
  const url = `http://localhost:8080/to_do_list/api/tasklist/${taskListId}/hasIncompleteTasks`;
  try {
    const response = await fetch(url);
    if (!response.ok) {
      throw new Error(`Response status: ${response.status}`);
    }

    const json = await response.json();
    console.log(json);

    if (json.hasIncompleteTasks) {
          const confirmDelete = confirm(
            "This list has incomplete tasks. Are you sure you want to delete it?"
          );

          if (confirmDelete) {
            // Proceed with delete
            deleteTaskList(taskListId);
          } else {
            console.log("User canceled the delete operation.");
          }
        } else {
          // Proceed with delete since no incomplete tasks
          deleteTaskList(taskListId);
        }
      } catch (error) {
        console.error("Error checking incomplete tasks:", error.message);
      }

}

async function deleteTaskList(taskListId) {
  const deleteUrl = `http://localhost:8080/to_do_list/api/tasklist/${taskListId}`;

  try {
    const response = await fetch(deleteUrl, { method: "DELETE" });
    if (response.ok) {
      alert("Task list deleted successfully.");
    } else {
      alert(`Failed to delete task list: ${response.statusText}`);
    }
  } catch (error) {
    console.error("Error deleting task list:", error.message);
  }
}
