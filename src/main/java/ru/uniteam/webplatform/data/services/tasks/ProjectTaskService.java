package ru.uniteam.webplatform.data.services.tasks;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.uniteam.models.exceptions.IncorrectTaskUndefinedException;
import ru.uniteam.models.exceptions.PermissionException;
import ru.uniteam.models.tasks.TaskStatus;
import ru.uniteam.models.tasks.TaskType;
import ru.uniteam.models.users.ProjectRole;
import ru.uniteam.webplatform.data.entities.ProjectEntity;
import ru.uniteam.webplatform.data.entities.ProjectTaskEntity;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.UserProjectEntity;
import ru.uniteam.webplatform.data.entities.dto.tasks.ProjectTaskGet;
import ru.uniteam.webplatform.data.entities.dto.tasks.ProjectTaskPost;
import ru.uniteam.webplatform.data.entities.dto.tasks.projectInfo.ProjectTaskContainer;
import ru.uniteam.webplatform.data.entities.dto.tasks.projectInfo.ProjectTasksContainer;
import ru.uniteam.webplatform.data.entities.dto.tasks.userinfo.UserTask;
import ru.uniteam.webplatform.data.entities.dto.tasks.userinfo.UserTasksContainer;
import ru.uniteam.webplatform.data.repositories.ProjectTaskRepository;
import ru.uniteam.webplatform.data.repositories.UserProjectRepository;
import ru.uniteam.webplatform.data.services.CommonHelper;
import ru.uniteam.webplatform.data.services.projects.ProjectMapper;
import ru.uniteam.webplatform.data.services.users.UserMapper;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectTaskService {
    private final ProjectTaskRepository projectTaskRepository;
    private final CommonHelper commonHelper;
    private final ProjectTaskMapper projectTaskMapper;
    private final UserMapper userMapper;
    private final ProjectMapper projectMapper;
    private final UserProjectRepository userProjectRepository;

    /*
    Задачи могут выдавать замы и капитан
    Задачи могут быть выданы заму и работнику
    зам. может выдавать задачи только работникам
    капитан может выдавать задачи замам и работникам
     */
    public ProjectTaskGet giveNewTask(UserEntity executor, long employeeId, long projectId, ProjectTaskPost newTask) {

        List<UserProjectEntity> members = commonHelper.getMembers(projectId); // Подучаем участников проекта
        if (members.isEmpty()) throw new IllegalArgumentException("Server error");

        // Проверяем полномочия исполняющего
        UserProjectEntity executorInProject = commonHelper.getUserFromMembersById(executor.getId(), members);
        if (!List.of(ProjectRole.CAPTAIN, ProjectRole.SUBCAPTAIN).contains(executorInProject.getProjectRole())) {
            throw new PermissionException("Only the sub captain and captain can assign tasks");
        }

        // Есть ли пользователь(он должен быть либо замом, либо сотрудником), которому выдана задача в проекте
        UserProjectEntity employeeInThisProject = members.stream()
                .filter(up -> up.getUserEntity().getId() == employeeId
                        && List.of(ProjectRole.EMPLOYEE, ProjectRole.SUBCAPTAIN).contains(up.getProjectRole()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No available user with id=" + employeeId + " " +
                        "for assigning the task not found in project with id=" + projectId));

        // 1.1 Если пользователь зам., то ему может выдать задачу только капитан
        // 1.2 Если пользователь обычный сотрудник, то ему могут выдать задачу как зам. так и капитан
        if ((employeeInThisProject.getProjectRole() == ProjectRole.SUBCAPTAIN
                && executorInProject.getProjectRole() == ProjectRole.CAPTAIN)
                || employeeInThisProject.getProjectRole() == ProjectRole.EMPLOYEE) {

            ProjectTaskEntity taskEntity = projectTaskMapper.toProjectTaskEntityFromPost(newTask,
                    employeeInThisProject.getUserEntity(),
                    executorInProject.getProjectEntity());
            projectTaskRepository.save(taskEntity);
            return projectTaskMapper.toProjectTaskGetFromEntity(taskEntity);
        } else {
            throw new PermissionException("Only the captain can assign a task to the sub");
        }
    }

    /*
    Принятие задачи в обработку
     */
    public ProjectTaskGet applyTask(UserEntity executor, long taskId) {
        ProjectTaskEntity task = getTaskById(taskId);

        if (task.getUserEntity() == executor) {
            task.setTaskStatus(TaskStatus.IN_PROGRESS);
            return projectTaskMapper.toProjectTaskGetFromEntity(task);
        }
        throw new IncorrectTaskUndefinedException();
    }

    // Все задачи проекта с исполнителями(доступны только участникам проекта)
    public ProjectTasksContainer getTasksInProject(UserEntity executor, long projectId, TaskStatus taskStatus,
                                                   TaskType taskType) {
        List<UserProjectEntity> members = commonHelper.getMembers(projectId);

        Map<Long, ProjectRole> roles = new HashMap<>();
        for (UserProjectEntity member : members) {
            roles.put(member.getUserEntity().getId(), member.getProjectRole());
        }

        if (members.stream().map(up -> up.getUserEntity()).toList().contains(executor)) {
            Set<ProjectTaskEntity> tasks = members.getFirst().getProjectEntity().getTasks();

            return new ProjectTasksContainer(tasks.stream()
                    .filter(pt -> (taskStatus == null || pt.getTaskStatus() == taskStatus)
                            && (taskType == null || pt.getTaskType() == taskType))
                    .map(pt -> new ProjectTaskContainer(
                            projectTaskMapper.toProjectTaskInfo(pt),
                            userMapper.toUserTaskInfoFromEntity(pt.getUserEntity(),
                                    roles.get(pt.getUserEntity().getId()))))
                    .toList(),
                    projectMapper.toGetProjectFromProjectEntity(members.getFirst().getProjectEntity()));


        }
        throw new PermissionException("The user with id=" + executor.getId() + " does not in project");
    }

    // Все задачи текущего пользователя
    public UserTasksContainer getUserTasks(UserEntity executor, TaskStatus taskStatus, TaskType taskType) {
        List<ProjectTaskEntity> tasks = projectTaskRepository.getUserTasks(executor);
        return new UserTasksContainer(
                tasks.stream()
                        .filter(pt -> (taskStatus == null || taskStatus == pt.getTaskStatus()) &&
                                (taskType == null || taskType == pt.getTaskType()))
                        .map(pte ->
                                new UserTask(projectMapper.toPrTaskInfoFromProjectEntity(pte.getProjectEntity()),
                                        projectTaskMapper.toProjectTaskInfo(pte)))
                        .toList()
        );
    }

    // Отправить задачу на ревью
    public ProjectTaskGet sendTaskOnReview(UserEntity executor, long taskId) {
        ProjectTaskEntity userTask = getTaskById(taskId);

        if (userTask.getUserEntity() == executor) {
            userTask.setTaskStatus(TaskStatus.ON_REVIEW);
            return projectTaskMapper.toProjectTaskGetFromEntity(userTask);
        }
        throw new PermissionException();

    }

    /* Провести ревью
    Проводить ревью могут только капитан и замы
    Ревью задач замов может принимать только капитан
     */
    private ProjectTaskGet makeReview(UserEntity executor, long taskId, TaskStatus taskStatus) {
        ProjectTaskEntity task = getTaskById(taskId);
        ProjectEntity project = task.getProjectEntity();

        UserProjectEntity executorInProject = userProjectRepository.findUserInProject(executor, project)
                .orElseThrow(IncorrectTaskUndefinedException::new);
        UserProjectEntity userInProject = userProjectRepository.findUserInProject(task.getUserEntity(), project)
                .orElseThrow(IncorrectTaskUndefinedException::new);


        if (List.of(ProjectRole.CANDIDATE, ProjectRole.CANCELLED).contains(userInProject.getProjectRole())) {
            throw new PermissionException();
        } else if ((userInProject.getProjectRole() == ProjectRole.SUBCAPTAIN &&
                executorInProject.getProjectRole() == ProjectRole.CAPTAIN)
                ||
                userInProject.getProjectRole() == ProjectRole.EMPLOYEE &&
                        List.of(ProjectRole.CAPTAIN, ProjectRole.SUBCAPTAIN)
                                .contains(executorInProject.getProjectRole())) {

            task.setTaskStatus(taskStatus);
            return projectTaskMapper.toProjectTaskGetFromEntity(task);
        }

        throw new PermissionException();
    }

    public ProjectTaskGet underDevelopmentTask(UserEntity executor, long taskId) {
        return makeReview(executor, taskId, TaskStatus.UNDER_DEVELOPMENT);
    }

    public ProjectTaskGet completeTask(UserEntity executor, long taskId) {
        return makeReview(executor, taskId, TaskStatus.COMPLETED);
    }

    private ProjectTaskEntity getTaskById(long taskId) {
        return projectTaskRepository.findById(taskId)
                .orElseThrow(IncorrectTaskUndefinedException::new);
    }
}
