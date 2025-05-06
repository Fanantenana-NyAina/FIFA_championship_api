package com.fifa_api.models.match;

import com.fifa_api.models.Club;
import com.fifa_api.models.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Goal {
    private UUID idGoal;
    private UUID match;
    private Player player;
    private Club club;
    private int minutes;
    private Boolean isOwnGoal;
}
