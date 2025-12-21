package com.yassin.portfolio.service;

import com.yassin.portfolio.model.Project;
import com.yassin.portfolio.repository.ProjectRepository;
import com.yassin.portfolio.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private Project testProject;

    @BeforeEach
    void setUp() {
        testProject = Project.builder()
                .setId(1L)
                .setTitle("Test Project")
                .setShortDescription("A test project")
                .setTechnologies(Arrays.asList("Java", "Spring Boot"))
                .setFeatured(true)
                .build();
    }

    @Test
    @DisplayName("Should return all projects ordered by display order")
    void getAllProjects_ShouldReturnAllProjects() {
        // Given
        List<Project> expectedProjects = Arrays.asList(testProject);
        when(projectRepository.findAllByOrderByDisplayOrderAsc())
                .thenReturn(expectedProjects);

        // When
        List<Project> actualProjects = projectService.getAllProjects();

        // Then
        assertThat(actualProjects).hasSize(1);
        assertThat(actualProjects.get(0).getTitle()).isEqualTo("Test Project");
        verify(projectRepository, times(1)).findAllByOrderByDisplayOrderAsc();
    }

    @Test
    @DisplayName("Should return featured projects only")
    void getFeaturedProjects_ShouldReturnFeaturedProjectsOnly() {
        // Given
        when(projectRepository.findByFeaturedTrueOrderByDisplayOrderAsc())
                .thenReturn(Arrays.asList(testProject));

        // When
        List<Project> featuredProjects = projectService.getFeaturedProjects();

        // Then
        assertThat(featuredProjects).allMatch(Project::getFeatured);
        verify(projectRepository, times(1)).findByFeaturedTrueOrderByDisplayOrderAsc();
    }

    @Test
    @DisplayName("Should return project by ID when exists")
    void getProjectById_WhenProjectExists_ShouldReturnProject() {
        // Given
        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));

        // When
        Optional<Project> result = projectService.getProjectById(1L);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Test Project");
    }

    @Test
    @DisplayName("Should return empty when project does not exist")
    void getProjectById_WhenProjectDoesNotExist_ShouldReturnEmpty() {
        // Given
        when(projectRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<Project> result = projectService.getProjectById(999L);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should save project successfully")
    void saveProject_ShouldSaveAndReturnProject() {
        // Given
        when(projectRepository.save(any(Project.class))).thenReturn(testProject);

        // When
        Project savedProject = projectService.saveProject(testProject);

        // Then
        assertThat(savedProject).isNotNull();
        assertThat(savedProject.getTitle()).isEqualTo("Test Project");
        verify(projectRepository, times(1)).save(testProject);
    }
}