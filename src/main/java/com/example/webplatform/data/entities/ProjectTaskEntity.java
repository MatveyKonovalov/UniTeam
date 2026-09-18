package com.example.webplatform.data.entities;

import com.example.models.tasks.TaskStatus;
import com.example.models.tasks.TaskType;
import jakarta.persistence.*;

@Entity
@Table(name = "projects_tasks")
public class ProjectTaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "task_status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @Column(name = "task_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity projectEntity;

    public ProjectTaskEntity() {
    }

    public ProjectTaskEntity(String title, String description, TaskStatus taskStatus,
                             TaskType taskType, ProjectEntity projectEntity) {
        this.description = description;
        this.title = title;
        this.taskStatus = taskStatus;
        this.taskType = taskType;
        this.projectEntity = projectEntity;
    }

    // getters
    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public String getTitle() {
        return title;
    }

    public ProjectEntity getProjectEntity() {
        return projectEntity;
    }

    // setters
    public void setDescription(String description) {
        this.description = description;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof ProjectTaskEntity that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
