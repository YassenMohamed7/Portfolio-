package com.yassin.portfolio.model;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "experiences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String jobTitle;

    private String location;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate; // null means current position

    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "experience_responsibilities",
            joinColumns = @JoinColumn(name = "experience_id"))
    @Column(name = "responsibility")
    @Builder.Default
    private List<String> responsibilities = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "experience_technologies",
            joinColumns = @JoinColumn(name = "experience_id"))
    @Column(name = "technology")
    @Builder.Default
    private List<String> technologiesUsed = new ArrayList<>();

    @Builder.Default
    private Integer displayOrder = 0;

    public boolean isCurrent() {
        return endDate == null;
    }
}