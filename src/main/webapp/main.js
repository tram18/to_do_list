/*
user clicks X
we check the REST API hasIncompleteItems
if it returns true, then display a confirmation warning


*/

async function hasIncompleteItems(taskListId) {
  const url = `http://localhost:8080/to_do_list/api/tasklist/${taskListId}/hasIncompleteTasks`;
  try {
    const response = await fetch(url); // Await the fetch call
    console.log(response);

    if (!response.ok) {
      throw new Error(`Response status: ${response.status}`);
    }

    const json = await response.json(); // Await the parsing of the JSON
    console.log(json);

    return json.hasIncompleteTasks; // Return the desired property
  } catch (error) {
    console.error("Error checking incomplete tasks:", error.message);
  }
}

async function deleteTaskList(taskListId) {
  const deleteUrl = `http://localhost:8080/to_do_list/api/tasklist/${taskListId}`;

  try {
    const response = await fetch(deleteUrl, { method: "DELETE" });
    hideTaskListElement(taskListId); //todo the selected box still appear the list

    if (response.ok) {
      console.log("Task list deleted successfully.");
    } else {
      console.log(`Failed to delete task list: ${response.statusText}`);
    }
  } catch (error) {
    console.error("Error deleting task list:", error.message);
  }
}

async function confirmDelete(taskListId) {
    const isIncomplete = await hasIncompleteItems(taskListId);

    console.log('isIncomplete ' + isIncomplete  );

    if(isIncomplete) {
        const warningElementId = 'warning-' + taskListId;
        const warningDiv = document.getElementById(warningElementId);
        warningDiv.style.visibility = 'visible';
    } else {
         deleteTaskList(taskListId);
//         hideTaskListElement(taskListId);
    }

}

function hideTaskListElement(taskListId) {
    const d = document.getElementById('task-list-' + taskListId);
    d.remove();
}

function cancelDelete(taskListId) {
    const warningElementId = 'warning-' + taskListId;
    const warningDiv = document.getElementById(warningElementId);
    warningDiv.style.visibility = 'hidden';
}