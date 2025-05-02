package com.fifa_api.endpoints.rest;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClubRest {
    private UUID id;
    private String name;
    private String acronym;
    private Integer yearCreation;
    private String stadium;
    private CoachRest coach;
}
