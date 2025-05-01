package com.fifa_api.endpoints.mappers;

import com.fifa_api.endpoints.rest.SeasonRest;
import com.fifa_api.models.Season;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class SeasonRestMapper implements Function<Season, SeasonRest> {
    @Override
    public SeasonRest apply(Season season) {
        return new SeasonRest(
                season.getSeasonYear(),
                season.getSeasonAlias(),
                season.getSeasonId(),
                season.getSeasonStatus()
        );
    }

    public SeasonRest toSeasonRest(Season season) {
        SeasonRest seasonRest = new SeasonRest();

        seasonRest.setYear(season.getSeasonYear());
        seasonRest.setAlias(season.getSeasonAlias());
        seasonRest.setId(season.getSeasonId());
        seasonRest.setStatus(season.getSeasonStatus());

        return seasonRest;
    }
}
