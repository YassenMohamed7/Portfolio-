package com.yassin.portfolio.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "skills")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private SkillCategory category;

    @Column(nullable = false)
    private Integer proficiencyLevel; // 1-5

    private Integer yearsOfExperience;

    private String iconClass; // For Font Awesome or similar

    @Builder.Default
    private Integer displayOrder = 0;

    public enum SkillCategory {
        FUNDAMENTALS,
        LANGUAGE,
        FRAMEWORK,
        DATABASE,
        DEVOPS,
        TESTING,
        MESSAGING,
        CLOUD,
        TOOLS
    }
}