package com.fifa_api.endpoints.rest.matchRest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Scorers {
    private PlayerMinimumInfo player;
    private int minuteOfGoal;
    private Boolean ownGoal;
}
