package com.fifa_api.dao.mappers;

import com.fifa_api.models.Coach;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.UUID;
import java.util.function.Function;

@Component
public class CoachMapper implements Function<ResultSet, Coach> {

    @SneakyThrows
    @Override
    public Coach apply(ResultSet resultSet) {
        // id_entraineur | nom | nationalite | age | id_club
        UUID coachId = UUID.fromString(resultSet.getString("id_entraineur"));
        String coachName = resultSet.getString("nom");
        String coachNationality = resultSet.getString("nationalite");
        UUID clubId = UUID.fromString(resultSet.getString("id_club"));

        Coach coach = new Coach();
        coach.setCoachId(coachId);
        coach.setCoachName(coachName);
        coach.setCoachNationality(coachNationality);
        coach.setClubId(clubId);

        return coach;
    }
}
