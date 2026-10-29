package ru.uniteam.webplatform.controllers.closed;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.uniteam.webplatform.controllers.CommonPrefix;
import ru.uniteam.webplatform.data.entities.dto.tasks.ProjectTaskGet;
import ru.uniteam.webplatform.data.entities.dto.tasks.ProjectTaskPost;
import ru.uniteam.webplatform.data.entities.dto.tasks.projectInfo.ProjectTasksContainer;
import ru.uniteam.webplatform.data.entities.dto.tasks.userinfo.UserTasksContainer;
import ru.uniteam.webplatform.data.services.tasks.ProjectTaskService;
import ru.uniteam.webplatform.data.services.users.UserService;

@RestController
@RequestMapping(CommonPrefix.PREFIX + "users")
public class TaskController extends UserGetterFromRequest {
    private final ProjectTaskService projectTaskService;

    public TaskController(ProjectTaskService projectTaskService, UserService userService) {
        super(userService);
        this.projectTaskService = projectTaskService;
    }

    @PostMapping("/tasks/give_task/{projectId}/{userId}")
    public ResponseEntity<ProjectTaskGet> giveTask(@PathVariable long userId,
                                                   @PathVariable long projectId,
                                                   @RequestBody ProjectTaskPost newTask) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectTaskService.giveNewTask(getUserFromRequest(), userId, projectId, newTask));
    }

    @PostMapping("/tasks/{taskId}")
    public ResponseEntity<ProjectTaskGet> applyTask(@PathVariable long taskId){
        return ResponseEntity.ok(projectTaskService.applyTask(getUserFromRequest(), taskId));
    }

    @PostMapping("/tasks/project/{projectId}")
    public ResponseEntity<ProjectTasksContainer> getTasksInProject(@PathVariable long projectId){
        return ResponseEntity.ok(projectTaskService.getTasksInProject(getUserFromRequest(), projectId));
    }

    @PostMapping("/tasks")
    public ResponseEntity<UserTasksContainer> getTasksByCurrentUser(){
        return ResponseEntity.ok(projectTaskService.getUserTasks(getUserFromRequest()));
    }
}
