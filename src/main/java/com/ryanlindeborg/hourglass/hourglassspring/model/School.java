package com.ryanlindeborg.hourglass.hourglassspring.model;

import com.ryanlindeborg.hourglass.hourglassspring.model.api.json.SchoolJson;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "school")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

    public SchoolJson createSchoolJson() {
        SchoolJson schoolJson = SchoolJson.builder()
                .id(id)
                .name(name)
                .build();

        return schoolJson;
    }
}
