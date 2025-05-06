package com.fifa_api.dao.mappers.match;

import com.fifa_api.dao.operations.ClubCRUDOperation;
import com.fifa_api.dao.operations.SeasonCRUDOperation;
import com.fifa_api.dao.operations.match.GoalCRUDOperation;
import com.fifa_api.models.Status;
import com.fifa_api.models.match.Matches;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class MatchMapper implements Function<ResultSet, Matches> {
    private final ClubCRUDOperation clubCRUDOperation;
    private final SeasonCRUDOperation seasonCRUDOperation;
    private final GoalCRUDOperation goalCRUDOperation;

    // id_match | date_heure | stade | id_club_domicile | id_club_exterieur | score_domicile | score_exterieur | id_saison | status
    @SneakyThrows
    @Override
    public Matches apply(ResultSet rs) {
        Matches match = new Matches();

        UUID idMatch = UUID.fromString(rs.getString("id_match"));
        match.setIdMatch(idMatch);

        match.setMatchDateTime(rs.getObject("date_heure", LocalDateTime.class));
        match.setStadium(rs.getString("stade"));

        UUID homeClubId = UUID.fromString(rs.getString("id_club_domicile"));
        match.setHomeClub(clubCRUDOperation.getById(homeClubId));

        UUID awayClubId = UUID.fromString(rs.getString("id_club_exterieur"));
        match.setAwayClub(clubCRUDOperation.getById(awayClubId));

        match.setHomeScore(rs.getInt("score_domicile"));
        match.setAwayScore(rs.getInt("score_exterieur"));

        UUID seasonID = UUID.fromString(rs.getString("id_saison"));
        match.setSeason(seasonCRUDOperation.getById(seasonID));

        Status status = Status.valueOf(rs.getString("status"));
        match.setStatus(status);
        match.setGoalsInMatch(goalCRUDOperation.getGoalsInMatch(idMatch));

        return match;
    }
}
