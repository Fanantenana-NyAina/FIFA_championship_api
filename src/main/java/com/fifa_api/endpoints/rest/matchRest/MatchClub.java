package com.fifa_api.endpoints.rest.matchRest;

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
public class MatchClub {
    private UUID id;
    private String name;
    private String acronym;
    private int score;
    private List<Scorers> scorers;
}
