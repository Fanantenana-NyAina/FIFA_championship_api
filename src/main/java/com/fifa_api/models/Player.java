package com.fifa_api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Player {
    private UUID playerId;
    private String playerName;
    private Integer playerNumber;
    private Post post;
    private String playerNationality;
    private Integer playerAge;
    @Nullable
    private Club club;
}
