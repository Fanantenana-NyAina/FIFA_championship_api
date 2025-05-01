package com.fifa_api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Coach {
    private UUID coachId;
    private String coachName;
    private String coachNationality;
    private Integer coachAge;
    private UUID clubId;
}
