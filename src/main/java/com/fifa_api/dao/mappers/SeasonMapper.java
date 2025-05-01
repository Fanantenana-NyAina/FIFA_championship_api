package com.fifa_api.dao.mappers;

import com.fifa_api.models.Season;
import com.fifa_api.models.Status;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.UUID;
import java.util.function.Function;

@Component
public class SeasonMapper implements Function<ResultSet, Season> {

    @SneakyThrows
    @Override
    public Season apply(ResultSet resultSet) {
        UUID seasonId = UUID.fromString(resultSet.getString("id_saison"));
        Integer seasonYear = resultSet.getInt("year");
        String seasonAlias = resultSet.getString("season_year");
        Status status = Status.valueOf(resultSet.getString("status"));

        Season season = new Season();
        season.setSeasonId(seasonId);
        season.setSeasonYear(seasonYear);
        season.setSeasonAlias(seasonAlias);
        season.setSeasonStatus(status);

        return season;
    }
}
