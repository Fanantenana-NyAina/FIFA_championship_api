package com.fifa_api.endpoints.rest.statsRest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PlayerStatisticsRest {
    private int scoredGoals;
    private PlayingTime playingTime;
}
