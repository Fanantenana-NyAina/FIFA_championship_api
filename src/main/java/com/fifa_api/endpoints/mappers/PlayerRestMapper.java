package com.fifa_api.endpoints.mappers;

import com.fifa_api.endpoints.rest.CreateOrUpdatePlayer;
import com.fifa_api.endpoints.rest.PlayerRest;
import com.fifa_api.models.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PlayerRestMapper {
    private final ClubRestMapper clubRestMapper;

    public PlayerRest toRest(Player player) {
        if (player == null) {
            return null;
        }

        PlayerRest playerRest = new PlayerRest();
        playerRest.setId(player.getPlayerId());
        playerRest.setName(player.getPlayerName());
        playerRest.setNumber(player.getPlayerNumber());
        playerRest.setPosition(player.getPost());
        playerRest.setNationality(player.getPlayerNationality());
        playerRest.setAge(player.getPlayerAge());
        playerRest.setClub(player.getClub() != null ? clubRestMapper.apply(player.getClub()) : null);

        return playerRest;
    }

    public Player toModel(CreateOrUpdatePlayer dto) {
        if (dto == null) {
            return null;
        }

        Player player = new Player();
        player.setPlayerId(dto.getId() != null ? dto.getId() : UUID.randomUUID());
        player.setPlayerName(dto.getName());
        player.setPlayerNumber(dto.getNumber());
        player.setPost(dto.getPosition());
        player.setPlayerNationality(dto.getNationality());
        player.setPlayerAge(dto.getAge());

        return player;
    }
}
