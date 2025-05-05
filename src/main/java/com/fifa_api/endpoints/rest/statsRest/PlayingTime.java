package com.fifa_api.endpoints.rest.statsRest;

import com.fifa_api.models.stats.DurationUnit;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PlayingTime {
    private int value;
    private DurationUnit durationUnit;
}
