package ru.uniteam.webplatform.controllers.closed;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import ru.uniteam.models.tasks.TaskStatus;
import ru.uniteam.models.tasks.TaskType;
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

    @PostMapping("/tasks/apply/{taskId}")
    public ResponseEntity<ProjectTaskGet> applyTask(@PathVariable long taskId) {
        return ResponseEntity.ok(projectTaskService.applyTask(getUserFromRequest(), taskId));
    }

    @PostMapping("/tasks/project/{projectId}")
    public ResponseEntity<ProjectTasksContainer> getTasksInProject(@PathVariable long projectId,
                                                                   @RequestParam(required = false) TaskStatus taskStatus,
                                                                   @RequestParam(required = false) TaskType taskType) {
        return ResponseEntity.ok(projectTaskService
                .getTasksInProject(getUserFromRequest(), projectId, taskStatus, taskType));
    }

    @PostMapping("/tasks")
    public ResponseEntity<UserTasksContainer> getTasksByCurrentUser(@RequestParam(required = false) TaskStatus taskStatus,
                                                                    @RequestParam(required = false) TaskType taskType) {
        return ResponseEntity.ok(projectTaskService.getUserTasks(getUserFromRequest(), taskStatus, taskType));
    }

    @PostMapping("/tasks/onreview/{taskId}")// Отправить задачу на ревью
    public ResponseEntity<ProjectTaskGet> sendTaskOnReview(@PathVariable long taskId) {
        return ResponseEntity.ok(projectTaskService.sendTaskOnReview(getUserFromRequest(), taskId));
    }

    @PostMapping("/task/complete/{taskId}")
    public ResponseEntity<ProjectTaskGet> completeTask(@PathVariable long taskId){
        return ResponseEntity.ok(projectTaskService.completeTask(getUserFromRequest(), taskId));
    }

    @PostMapping("/tasks/under_develop/{taskId}")
    public ResponseEntity<ProjectTaskGet> underTask(@PathVariable long taskId){
        return ResponseEntity.ok(projectTaskService.underDevelopmentTask(getUserFromRequest(), taskId));
    }
}
