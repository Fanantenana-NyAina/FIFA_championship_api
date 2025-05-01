package com.fifa_api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Season {
    private UUID seasonId;
    private Integer seasonYear;
    private String seasonAlias;
    private Status seasonStatus;
}
