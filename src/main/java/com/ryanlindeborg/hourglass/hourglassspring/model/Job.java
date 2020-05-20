package com.ryanlindeborg.hourglass.hourglassspring.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "position")
    private String position;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    @Enumerated(value = EnumType.STRING)
    private Industry industry;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Enumerated(value = EnumType.STRING)
    private JobType jobType;
}
