package com.yassin.portfolio.service;

import com.yassin.portfolio.model.Skill;
import com.yassin.portfolio.model.Skill.SkillCategory;
import java.util.List;
import java.util.Map;
public interface SkillService {
    List<Skill> getAllSkills();
    Map<SkillCategory, List<Skill>> getAllSkillsGroupedByCategory();
    List<Skill> getSkillsByCategory(SkillCategory category);
    Skill saveSkill(Skill skill);
    void deleteSkill(Long id);
}
