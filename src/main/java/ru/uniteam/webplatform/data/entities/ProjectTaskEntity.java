package ru.uniteam.webplatform.data.entities;

import ru.uniteam.models.tasks.TaskStatus;
import ru.uniteam.models.tasks.TaskType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "projects_tasks")
@Getter
public class ProjectTaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    @Setter
    private String title;

    @Column(name = "description", nullable = false)
    @Setter
    private String description;

    @Column(name = "task_status", nullable = false, length = 20)
    @Setter
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @Column(name = "task_type", nullable = false, length = 50)
    @Setter
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
