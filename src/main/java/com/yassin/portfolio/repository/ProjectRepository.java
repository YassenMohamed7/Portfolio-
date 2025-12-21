package com.yassin.portfolio.repository;


import com.yassin.portfolio.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByFeaturedTrueOrderByDisplayOrderAsc();

    List<Project> findAllByOrderByDisplayOrderAsc();

    @Query("SELECT p FROM Project p WHERE :technology MEMBER OF p.technologies")
    List<Project> findByTechnology(String technology);

    @Query("SELECT DISTINCT t FROM Project p JOIN p.technologies t")
    List<String> findAllTechnologies();
}