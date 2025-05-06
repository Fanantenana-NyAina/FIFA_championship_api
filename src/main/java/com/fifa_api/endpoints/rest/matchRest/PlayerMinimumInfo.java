package com.fifa_api.endpoints.rest.matchRest;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PlayerMinimumInfo {
    private UUID id;
    private String name;
    private int number;
}
