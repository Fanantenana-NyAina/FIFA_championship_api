package com.fifa_api.services;

import com.fifa_api.dao.operations.PlayerCRUDOperation;
import com.fifa_api.models.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerCRUDOperation playerCRUDOperation;

    public List<Player> getFilteredPlayer(String name,
                                          Integer ageMin,
                                          Integer ageMax,
                                          String clubName,
                                          Integer page,
                                          Integer size) {

        List<Player> players = playerCRUDOperation.getAll(page, size);

        return players.stream()
                .filter(player -> matchesName(player, name))
                .filter(player -> matchesAges(player, ageMin, ageMax))
                .filter(player -> matchesClub(player, clubName))
                .toList();
    }

    private boolean matchesName(Player player, String name) {
        if (name == null) {
            return true;
        }

        String playerName = player.getPlayerName().toLowerCase();
        String searchName = name.toLowerCase();
        return playerName.contains(searchName);
    }

    private boolean matchesClub(Player player, String club) {
        if (club == null) {
            return true;
        }

        String clubName = player.getClub().getClubName().toLowerCase();
        String searchClub = club.toLowerCase();

        return clubName.contains(searchClub);
    }

    private boolean matchesAges(Player player, Integer ageMin, Integer ageMax) {
            Integer age = player.getPlayerAge();
            return (ageMin == null || age >= ageMin) && (ageMax == null || age <= ageMax);
    }
}
