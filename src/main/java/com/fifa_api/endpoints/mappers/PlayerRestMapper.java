package com.fifa_api.endpoints.mappers;

import com.fifa_api.endpoints.rest.PlayerRest;
import com.fifa_api.models.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlayerRestMapper {
    private final ClubRestMapper clubRestMapper;

    public PlayerRest toRest(Player player) {
        PlayerRest playerRest = new PlayerRest();

        playerRest.setId(player.getPlayerId());
        playerRest.setName(player.getPlayerName());
        playerRest.setNumber(player.getPlayerNumber());
        playerRest.setPosition(player.getPost());
        playerRest.setNationality(player.getPlayerNationality());
        playerRest.setAge(player.getPlayerAge());
       playerRest.setClub(clubRestMapper.apply(player.getClub()));

        return playerRest;
    }
}
