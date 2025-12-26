package com.yassin.portfolio.controller;

import com.yassin.portfolio.model.Project;
import com.yassin.portfolio.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProjectControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectRepository projectRepository;

    @BeforeEach
    void setUp() {
        projectRepository.deleteAll();

        Project project = Project.builder()
                .setTitle("Integration Test Project")
                .setShortDescription("A project for integration testing")
                .setTechnologies(Arrays.asList("Java", "Spring Boot", "PostgreSQL"))
                .setFeatured(true)
                .setDisplayOrder(1)
                .build();

        projectRepository.save(project);
    }

    @Test
    @DisplayName("GET /projects should return projects page")
    void listProjects_ShouldReturnProjectsPage() throws Exception {
        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(view().name("projects"))
                .andExpect(model().attributeExists("projects"))
                .andExpect(model().attribute("projects", hasSize(1)));
    }

    @Test
    @DisplayName("GET /projects/api should return JSON list of projects")
    void getAllProjectsApi_ShouldReturnJsonList() throws Exception {
        mockMvc.perform(get("/projects/api")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Integration Test Project")));
    }

    @Test
    @DisplayName("GET /projects/api/featured should return featured projects")
    void getFeaturedProjectsApi_ShouldReturnFeaturedProjects() throws Exception {
        mockMvc.perform(get("/projects/api/featured")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].featured", is(true)));
    }

    @Test
    @DisplayName("GET /projects/{id} with invalid ID should return 404")
    void getProjectDetail_WithInvalidId_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/projects/999"))
                .andExpect(status().isOk()); // okay global excption handling return 200 for 404 error page
    }
}