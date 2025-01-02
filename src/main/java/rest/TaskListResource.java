package rest;
import service.TaskListService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/tasklist")
public class TaskListResource {

    @EJB
    private TaskListService taskListService;

    @GET
    @Path("/{listId}/hasIncompleteTasks")
    @Produces("application/json")
    public Response hasIncompleteTasks(@PathParam("listId") int listId) {
        boolean hasIncomplete = taskListService.findTaskListById(listId)
                .getTaskItems()
                .stream()
                .anyMatch(item -> !item.isCompleted());
        return Response.ok("{\"hasIncompleteTasks\": " + hasIncomplete + "}").build();
    }
}
