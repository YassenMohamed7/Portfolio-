package com.yassin.portfolio.repository;


import com.yassin.portfolio.model.Skill;
import com.yassin.portfolio.model.Skill.SkillCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    List<Skill> findByCategoryOrderByDisplayOrderAsc(SkillCategory category);

    List<Skill> findAllByOrderByCategoryAscDisplayOrderAsc();
}