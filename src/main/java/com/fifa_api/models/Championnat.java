package com.fifa_api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Championnat {
    private UUID championnatId;
    private String championnatName;
    private String pays;
    private List<Club> clubs;
}
