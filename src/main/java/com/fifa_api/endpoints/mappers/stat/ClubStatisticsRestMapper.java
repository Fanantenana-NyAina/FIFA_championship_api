package com.fifa_api.endpoints.mappers.stat;

import com.fifa_api.endpoints.mappers.ClubRestMapper;
import com.fifa_api.endpoints.rest.statsRest.ClubStatisticsRest;
import com.fifa_api.models.stats.ClubStatistics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ClubStatisticsRestMapper implements Function<ClubStatistics, ClubStatisticsRest> {
    private final ClubRestMapper clubRestMapper;
    @Override
    public ClubStatisticsRest apply(ClubStatistics clubStatistics) {
        if (clubStatistics == null) {
            return null;
        }

        return new ClubStatisticsRest(
                clubRestMapper.apply(clubStatistics.getClub()),
                clubStatistics.getRankingPoints(),
                clubStatistics.getScoredGoals(),
                clubStatistics.getConcededGoals(),
                clubStatistics.getDifferenceGoals(),
                clubStatistics.getCleanSheetNumber()
        );
    }
}
