package com.fifa_api.endpoints;

import com.fifa_api.endpoints.mappers.PlayerRestMapper;
import com.fifa_api.endpoints.rest.PlayerRest;
import com.fifa_api.models.Player;
import com.fifa_api.services.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlayerRestController {
    private final PlayerService playerService;
    private final PlayerRestMapper playerRestMapper;

    @GetMapping("/players")
    public ResponseEntity<Object> getPlayers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer ageMinimum,
            @RequestParam(required = false) Integer ageMaximum,
            @RequestParam(required = false) String clubName,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size
    ) {
        List<Player> players = playerService.getFilteredPlayer(name, ageMinimum, ageMaximum, clubName, page, size);
        List<PlayerRest> playerRests = players.stream().map(
                playerRestMapper::toRest
        ).toList();

        return ResponseEntity.status(HttpStatus.OK).body(playerRests);
    }
}
