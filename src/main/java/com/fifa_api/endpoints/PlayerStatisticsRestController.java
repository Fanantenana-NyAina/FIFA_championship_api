package com.fifa_api.endpoints;

import com.fifa_api.endpoints.mappers.stat.PlayerStatisticRestMapper;
import com.fifa_api.endpoints.rest.statsRest.PlayerStatisticsRest;
import com.fifa_api.models.stats.PlayerStatistics;
import com.fifa_api.services.stats.PlayerStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PlayerStatisticsRestController {
    public final PlayerStatisticsService playerStatisticsService;
    public final PlayerStatisticRestMapper playerStatisticRestMapper;

    @GetMapping("players/{id}/statistics/{seasonYear}")
    public ResponseEntity<Object> getPlayerStatistics(
            @PathVariable UUID id,
            @PathVariable String seasonYear
    ) {

        List<PlayerStatistics> playerStatistics = Collections.singletonList(playerStatisticsService.getPlayerStatistics(id, seasonYear));
        List<PlayerStatisticsRest> playerStatisticsRest = playerStatistics.stream().map(
                playerStatisticRestMapper
        ).toList();

        return ResponseEntity.ok(playerStatisticsRest);
    }
}
