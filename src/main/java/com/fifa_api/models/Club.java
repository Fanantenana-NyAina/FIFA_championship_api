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
public class Club {
    private UUID clubId;
    private String clubName;
    private String clubAcronym;
    private Integer creationYear;
    private String stadium;
    @Nullable
    private Coach coach;
    private UUID championshipId;
}
