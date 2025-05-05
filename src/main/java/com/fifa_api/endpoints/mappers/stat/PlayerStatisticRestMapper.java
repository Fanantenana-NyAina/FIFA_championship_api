package com.fifa_api.endpoints.mappers.stat;

import com.fifa_api.endpoints.rest.statsRest.PlayerStatisticsRest;
import com.fifa_api.endpoints.rest.statsRest.PlayingTime;
import com.fifa_api.models.stats.PlayerStatistics;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PlayerStatisticRestMapper implements Function<PlayerStatistics, PlayerStatisticsRest> {
    @Override
    public PlayerStatisticsRest apply(PlayerStatistics playerStatistics) {
        if (playerStatistics == null) {
            return null;
        }

        return new PlayerStatisticsRest(
                playerStatistics.getGoalsScored(),
                new PlayingTime(
                        playerStatistics.getPlayTimeValue(),
                        playerStatistics.getDuration()
                )
        );
    }
}
