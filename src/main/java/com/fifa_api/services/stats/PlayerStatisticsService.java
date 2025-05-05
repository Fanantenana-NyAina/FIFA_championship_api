package com.fifa_api.services.stats;

import com.fifa_api.dao.operations.stats.PlayersStatisticsCRUDOperation;
import com.fifa_api.models.stats.PlayerStatistics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerStatisticsService {
    private final PlayersStatisticsCRUDOperation playersStatisticsCRUDOperation;

    public PlayerStatistics getPlayerStatistics(UUID playerId, String seasonYear) {
        return playersStatisticsCRUDOperation.findByPlayerAndSeason(playerId, seasonYear);
    }

}
