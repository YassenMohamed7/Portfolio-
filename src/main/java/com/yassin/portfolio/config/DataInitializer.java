package com.yassin.portfolio.config;

import com.yassin.portfolio.model.Project;
import com.yassin.portfolio.model.Skill;
import com.yassin.portfolio.service.ProjectService;
import com.yassin.portfolio.service.SkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final ProjectService projectService;
    private final SkillService skillService;

    @Override
    public void run(String... args) throws Exception {
//        Adding Skills
        Skill oop = Skill.builder()
                .setName("OOP")
                .setCategory(Skill.SkillCategory.FUNDAMENTALS)
                .setProficiencyLevel(5)
                .setDisplayOrder(1)
                .setYearsOfExperience(3)
                .build();
        skillService.saveSkill(oop);
        Skill java_8 = Skill.builder()
                .setName("Java 8")
                .setCategory(Skill.SkillCategory.FUNDAMENTALS)
                .setProficiencyLevel(5)
                .setDisplayOrder(2)
                .setYearsOfExperience(3)
                .build();
        skillService.saveSkill(java_8);
        Skill java_se = Skill.builder()
                .setName("Java SE")
                .setCategory(Skill.SkillCategory.FUNDAMENTALS)
                .setProficiencyLevel(5)
                .setDisplayOrder(3)
                .setYearsOfExperience(3)
                .build();
        skillService.saveSkill(java_se);
        Skill solid_principles = Skill.builder()
                .setName("SOLID Principles")
                .setCategory(Skill.SkillCategory.FUNDAMENTALS)
                .setProficiencyLevel(5)
                .setDisplayOrder(4)
                .setYearsOfExperience(2)
                .build();
        skillService.saveSkill(solid_principles);
        Skill design_patterns = Skill.builder()
                .setName("GoF Design Patterns")
                .setCategory(Skill.SkillCategory.FUNDAMENTALS)
                .setProficiencyLevel(5)
                .setDisplayOrder(5)
                .setYearsOfExperience(1)
                .build();
        skillService.saveSkill(design_patterns);
        Skill sql = Skill.builder()
                .setName("SQL")
                .setCategory(Skill.SkillCategory.DATABASE)
                .setProficiencyLevel(5)
                .setDisplayOrder(1)
                .setYearsOfExperience(4)
                .build();
        skillService.saveSkill(sql);
        Skill postgresql = Skill.builder()
                .setName("PostgreSQL")
                .setCategory(Skill.SkillCategory.DATABASE)
                .setProficiencyLevel(5)
                .setDisplayOrder(2)
                .setYearsOfExperience(2)
                .build();
        skillService.saveSkill(postgresql);
        Skill mysql = Skill.builder()
                .setName("MySQL")
                .setCategory(Skill.SkillCategory.DATABASE)
                .setProficiencyLevel(5)
                .setDisplayOrder(3)
                .setYearsOfExperience(3)
                .build();
        skillService.saveSkill(mysql);
        Skill mongoDB = Skill.builder()
                .setName("MongoDB")
                .setCategory(Skill.SkillCategory.DATABASE)
                .setProficiencyLevel(5)
                .setDisplayOrder(4)
                .setYearsOfExperience(3)
                .build();
        skillService.saveSkill(mongoDB);
        Skill firebase = Skill.builder()
                .setName("Firebase")
                .setCategory(Skill.SkillCategory.DATABASE)
                .setProficiencyLevel(5)
                .setDisplayOrder(5)
                .setYearsOfExperience(3)
                .build();
        skillService.saveSkill(firebase);







        List<String> technologies = new ArrayList<>();
        technologies.add("Spring boot");
        technologies.add("Java 8");

        Project project = Project.builder()
                .setId(1L)
                .setTitle("Coupon Service")
                .setImageUrl("/images/coupon.avif")
                .setFeatured(Boolean.TRUE)
                .setGithubUrl("https://github.com/Fawry-Microservices/Coupon-Api/tree/backend")
                .setDisplayOrder(1)
                .setTechnologies(technologies)
                .build();
        projectService.saveProject(project);
        log.info("Creating a new Project: " + projectService.getProjectById(1L));
    }
}
