package com.yassin.portfolio.controller;


import com.yassin.portfolio.exception.ResourceNotFoundException;
import com.yassin.portfolio.model.Project;
import com.yassin.portfolio.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Projects", description = "Project management endpoints")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public String listProjects(Model model,
                               @RequestParam(required = false) String technology) {
        log.info("Fetching projects page, filter by technology: {}", technology);

        List<Project> projects;
        if (technology != null && !technology.isBlank()) {
            projects = projectService.getProjectsByTechnology(technology);
        } else {
            projects = projectService.getAllProjects();
        }

        model.addAttribute("projects", projects);
        model.addAttribute("technologies", projectService.getAllTechnologies());
        model.addAttribute("selectedTechnology", technology);
        return "projects";
    }

    @GetMapping("/{id}")
    public String projectDetail(@PathVariable Long id, Model model) {
        log.info("Fetching project detail for id: {}", id);

        Project project = projectService.getProjectById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));

        model.addAttribute("project", project);
        return "project-detail";
    }

    // REST API Endpoints
    @GetMapping("/api")
    @ResponseBody
    @Operation(summary = "Get all projects", description = "Returns a list of all projects")
    public ResponseEntity<List<Project>> getAllProjectsApi() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/api/featured")
    @ResponseBody
    @Operation(summary = "Get featured projects", description = "Returns a list of featured projects")
    public ResponseEntity<List<Project>> getFeaturedProjectsApi() {
        return ResponseEntity.ok(projectService.getFeaturedProjects());
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    @Operation(summary = "Get project by ID", description = "Returns a single project by its ID")
    public ResponseEntity<Project> getProjectByIdApi(@PathVariable Long id) {
        return projectService.getProjectById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
    }
}