package com.fifa_api.dao.mappers;

import com.fifa_api.models.Player;
import com.fifa_api.models.Post;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class PlayerMapper implements Function<ResultSet, Player> {

    @SneakyThrows
    @Override
    public Player apply(ResultSet resultSet) {
        UUID playerId = UUID.fromString(resultSet.getString("id_joueur"));
        String playerName = resultSet.getString("nom");
        Integer playerNumber = resultSet.getInt("numero");
        Post playerPosition = resultSet.getObject("poste", Post.class);
        String playerNationality = resultSet.getString("nationalite");
        Integer playerAge = resultSet.getInt("age");


        return new Player(
                playerId,
                playerName,
                playerNumber,
                playerPosition,
                playerNationality,
                playerAge
        );
    }
}
