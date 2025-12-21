package com.yassin.portfolio.service;


import com.yassin.portfolio.model.Project;
import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> getAllProjects();
    List<Project> getFeaturedProjects();
    Optional<Project> getProjectById(Long id);
    List<Project> getProjectsByTechnology(String technology);
    List<String> getAllTechnologies();
    Project saveProject(Project project);
    void deleteProject(Long id);
}