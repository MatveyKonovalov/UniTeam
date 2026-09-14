package com.example.webplatform.data.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "skills",
        uniqueConstraints = @UniqueConstraint(name = "uk_skill_title", columnNames = "title"))
public class SkillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long skillId;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    public SkillEntity() {}

    public SkillEntity(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SkillEntity that)) return false;
        return title != null && title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }
}
