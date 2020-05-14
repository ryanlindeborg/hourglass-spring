package com.ryanlindeborg.hourglass.hourglassspring.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "school_user")
public class SchoolUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Enumerated(value = EnumType.STRING)
    private Degree degree;
    @Column(name = "field_of_study")
    private String fieldOfStudy;
    @Column(name = "is_completed")
    private Boolean isCompleted;
}
