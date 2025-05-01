package com.fifa_api.dao.mappers;

import com.fifa_api.dao.operations.CoachCRUDOperation;
import com.fifa_api.models.Club;
import com.fifa_api.models.Coach;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ClubMapper implements Function<ResultSet, Club> {
    private final CoachCRUDOperation coachCRUDOperation;

    @SneakyThrows
    @Override
    public Club apply(ResultSet resultSet) {
        // id_club | nom | acronyme | annee_creation | nom_stade | id_championnat
        UUID clubId = UUID.fromString(resultSet.getString("id_club"));
        String clubName = resultSet.getString("nom");
        String acronym = resultSet.getString("acronyme");
        Integer creationYear = resultSet.getInt("annee_creation");
        String stadium = resultSet.getString("nom_stade");
        UUID championShipId = UUID.fromString(resultSet.getString("id_championnat"));

        Coach coach = coachCRUDOperation.findByIdClub(clubId);

        Club club = new Club();
        club.setClubId(clubId);
        club.setClubName(clubName);
        club.setClubAcronym(acronym);
        club.setCreationYear(creationYear);
        club.setStadium(stadium);
        club.setCoach(coach);
        club.setChampionshipId(championShipId);

        return club;
    }
}
