package com.yassin.portfolio.controller;


import com.yassin.portfolio.service.ProjectService;
import com.yassin.portfolio.service.SkillService;
import com.yassin.portfolio.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final ProjectService projectService;
    private final SkillService skillService;
    private final ExperienceService experienceService;

    @GetMapping("/")
    public String home(Model model) {
        log.info("Loading home page");
        model.addAttribute("featuredProjects", projectService.getFeaturedProjects());
        model.addAttribute("skills", skillService.getAllSkillsGroupedByCategory());
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        log.info("Loading about page");
        model.addAttribute("experiences", experienceService.getAllExperiences());
        model.addAttribute("skills", skillService.getAllSkillsGroupedByCategory());
        return "about";
    }
}