package com.fifa_api.dao.mappers.stats;

import com.fifa_api.dao.operations.PlayerCRUDOperation;
import com.fifa_api.dao.operations.SeasonCRUDOperation;
import com.fifa_api.models.stats.DurationUnit;
import com.fifa_api.models.stats.PlayerStatistics;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class PlayerStatsMapper implements Function<ResultSet, PlayerStatistics> {
    private final PlayerCRUDOperation playerCRUDOperation;
    private final SeasonCRUDOperation seasonCRUDOperation;

    @SneakyThrows
    @Override
    public PlayerStatistics apply(ResultSet rs) {
        PlayerStatistics stats = new PlayerStatistics();
        stats.setPlayerStatsId(UUID.fromString(rs.getString("id_stats")));

        UUID Idplayer = UUID.fromString(rs.getString("id_joueur"));
        stats.setPlayer(playerCRUDOperation.getById(Idplayer));

        UUID Idseason= UUID.fromString(rs.getString("id_saison"));
        stats.setSeason(seasonCRUDOperation.getById(Idseason));

        stats.setGoalsScored(rs.getInt("buts_marques"));
        stats.setOwnGoals(rs.getInt("buts_contre_son_camp"));
        stats.setPlayTimeValue(rs.getInt("duree_jeu_value"));
        stats.setDuration(DurationUnit.valueOf(rs.getString("duree_jeu_unit")));
        
        return stats;
    }
}
