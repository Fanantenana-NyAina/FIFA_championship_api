package com.fifa_api.dao.mappers.match;

import com.fifa_api.dao.operations.ClubCRUDOperation;
import com.fifa_api.dao.operations.PlayerCRUDOperation;
import com.fifa_api.models.match.Goal;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class GoalMapper implements Function<ResultSet, Goal> {
    private final ClubCRUDOperation clubCRUDOperation;
    private final PlayerCRUDOperation playerCRUDOperation;
    // id_but | id_match | id_joueur | id_club | minute | contre_son_camp

    @SneakyThrows
    @Override
    public Goal apply(ResultSet resultSet) {
        Goal goal = new Goal();

        goal.setIdGoal(UUID.fromString(resultSet.getString("id_but")));
        goal.setMatch(UUID.fromString(resultSet.getString("id_match")));

        UUID playerId = UUID.fromString(resultSet.getString("id_joueur"));
        goal.setPlayer(playerCRUDOperation.getById(playerId));

        UUID clubId = UUID.fromString(resultSet.getString("id_club"));
        goal.setClub(clubCRUDOperation.getById(clubId));

        goal.setMinutes(resultSet.getInt("minute"));
        goal.setIsOwnGoal(resultSet.getBoolean("contre_son_camp"));

        return goal;
    }
}
