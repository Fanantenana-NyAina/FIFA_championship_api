package com.fifa_api.endpoints;

import com.fifa_api.endpoints.mappers.stat.ClubStatisticsRestMapper;
import com.fifa_api.endpoints.rest.statsRest.ClubStatisticsRest;
import com.fifa_api.models.stats.ClubStatistics;
import com.fifa_api.services.stats.ClubStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClubsStatisticsRestController {
    private final ClubStatisticsService clubStatisticsService;
    private final ClubStatisticsRestMapper clubStatisticsRestMapper;

    @GetMapping("/clubs/statistics/{seasonYear}")
    public ResponseEntity<Object> getClubsStatisticsAtSeason(
            @PathVariable String seasonYear,
            @RequestParam(defaultValue = "false") Boolean hasToBeClassified)
    {
        List<ClubStatistics> clubsStatistics = clubStatisticsService.getClubsStatisticsAtSeason(seasonYear, hasToBeClassified);
        List<ClubStatisticsRest> clubsStatisticsRest = clubsStatistics.stream()
                .map(clubStatisticsRestMapper)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(clubsStatisticsRest);
    }
}
