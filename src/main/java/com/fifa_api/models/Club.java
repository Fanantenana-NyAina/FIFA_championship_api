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
public class Club {
    private UUID clubId;
    private String clubName;
    private String clubAcronym;
    private Integer creationYear;
    private String stadium;
    private Coach coach;
    private UUID championshipId;
}
