package com.yassin.portfolio.service.impl;



import com.yassin.portfolio.model.Project;
import com.yassin.portfolio.repository.ProjectRepository;
import com.yassin.portfolio.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    @Cacheable(value = "projects")
    public List<Project> getAllProjects() {
        log.info("Fetching all projects from database");
        return projectRepository.findAllByOrderByDisplayOrderAsc();
    }

    @Override
    @Cacheable(value = "featuredProjects")
    public List<Project> getFeaturedProjects() {
        log.info("Fetching featured projects");
        return projectRepository.findByFeaturedTrueOrderByDisplayOrderAsc();
    }

    @Override
    public Optional<Project> getProjectById(Long id) {
        log.info("Fetching project with id: {}", id);
        return projectRepository.findById(id);
    }

    @Override
    public List<Project> getProjectsByTechnology(String technology) {
        log.info("Fetching projects by technology: {}", technology);
        return projectRepository.findByTechnology(technology);
    }

    @Override
    @Cacheable(value = "technologies")
    public List<String> getAllTechnologies() {
        return projectRepository.findAllTechnologies();
    }

    @Override
    @Transactional
    @CacheEvict(value = {"projects", "featuredProjects", "technologies"}, allEntries = true)
    public Project saveProject(Project project) {
        log.info("Saving project: {}", project.getTitle());
        return projectRepository.save(project);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"projects", "featuredProjects", "technologies"}, allEntries = true)
    public void deleteProject(Long id) {
        log.info("Deleting project with id: {}", id);
        projectRepository.deleteById(id);
    }
}