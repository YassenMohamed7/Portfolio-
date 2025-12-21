package com.yassin.portfolio.service;

import com.yassin.portfolio.model.Experience;
import java.util.List;
import java.util.Optional;

public interface ExperienceService {
    List<Experience> getAllExperiences();
    Optional<Experience> getExperienceById(Long id);
    Experience saveExperience(Experience experience);
    void deleteExperience(Long id);
}