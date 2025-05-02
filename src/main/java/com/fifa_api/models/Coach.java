package com.fifa_api.models;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class Coach {
    private UUID coachId;
    private String coachName;
    private String coachNationality;
    private Integer coachAge;
    private UUID clubId;
}
