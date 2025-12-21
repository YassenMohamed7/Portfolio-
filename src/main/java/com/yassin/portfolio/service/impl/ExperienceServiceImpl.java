package com.yassin.portfolio.service.impl;

import com.yassin.portfolio.model.Experience;
import com.yassin.portfolio.repository.ExperienceRepository;
import com.yassin.portfolio.service.ExperienceService;
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
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;

    @Override
    @Cacheable(value = "experiences")
    public List<Experience> getAllExperiences() {
        log.info("Fetching all experiences from database");
        return experienceRepository.findAllByOrderByStartDateDesc();
    }

    @Override
    public Optional<Experience> getExperienceById(Long id) {
        log.info("Fetching experience with id: {}", id);
        return experienceRepository.findById(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "experiences", allEntries = true)
    public Experience saveExperience(Experience experience) {
        log.info("Saving experience: {}", experience.getJobTitle());
        return experienceRepository.save(experience);
    }

    @Override
    @Transactional
    @CacheEvict(value = "experiences", allEntries = true)
    public void deleteExperience(Long id) {
        log.info("Deleting experience with id: {}", id);
        experienceRepository.deleteById(id);
    }
}