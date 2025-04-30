package com.fifa_api.endpoints.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClubRest {
    private UUID id;
    private String name;
    private String acronym;
    private Integer yearCreation;
    private String stadium;
    private CoachRest coach;
}
