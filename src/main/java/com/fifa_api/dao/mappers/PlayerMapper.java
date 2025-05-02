package com.fifa_api.dao.mappers;

import com.fifa_api.dao.operations.ClubCRUDOperation;
import com.fifa_api.models.Club;
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
    private final ClubCRUDOperation clubCRUDOperation;

    @SneakyThrows
    @Override
    public Player apply(ResultSet resultSet) {
        UUID playerId = UUID.fromString(resultSet.getString("id_joueur"));
        String playerName = resultSet.getString("nom");
        Integer playerNumber = resultSet.getInt("numero");
        Post playerPosition = Post.valueOf(resultSet.getString("poste"));
        String playerNationality = resultSet.getString("nationalite");
        Integer playerAge = resultSet.getInt("age");

        String clubIdStr = resultSet.getString("id_club");
        Club club = null;
        if (clubIdStr != null) {
            UUID clubId = UUID.fromString(clubIdStr);
            club = clubCRUDOperation.getById(clubId);
        }

        Player player = new Player();
        player.setPlayerId(playerId);
        player.setPlayerName(playerName);
        player.setPlayerNumber(playerNumber);
        player.setPost(playerPosition);
        player.setPlayerNationality(playerNationality);
        player.setPlayerAge(playerAge);
        player.setClub(club);

        return player;
    }

}
