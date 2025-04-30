package com.fifa_api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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
    private String stadeName;
    private Coach coach;
    private List<Player> players;
}
