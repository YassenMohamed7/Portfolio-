package com.yassin.portfolio.service.impl;

import com.yassin.portfolio.model.Skill;
import com.yassin.portfolio.model.Skill.SkillCategory;
import com.yassin.portfolio.repository.SkillRepository;
import com.yassin.portfolio.service.SkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    @Override
    @Cacheable(value = "skills")
    public List<Skill> getAllSkills() {
        log.info("Fetching all skills from database");
        return skillRepository.findAllByOrderByCategoryAscDisplayOrderAsc();
    }

    @Override
    @Cacheable(value = "skillsByCategory")
    public Map<SkillCategory, List<Skill>> getAllSkillsGroupedByCategory() {
        log.info("Fetching skills grouped by category");
        return getAllSkills().stream()
                .collect(Collectors.groupingBy(Skill::getCategory));
    }

    @Override
    public List<Skill> getSkillsByCategory(SkillCategory category) {
        log.info("Fetching skills for category: {}", category);
        return skillRepository.findByCategoryOrderByDisplayOrderAsc(category);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"skills", "skillsByCategory"}, allEntries = true)
    public Skill saveSkill(Skill skill) {
        log.info("Saving skill: {}", skill.getName());
        return skillRepository.save(skill);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"skills", "skillsByCategory"}, allEntries = true)
    public void deleteSkill(Long id) {
        log.info("Deleting skill with id: {}", id);
        skillRepository.deleteById(id);
    }
}