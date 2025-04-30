package com.fifa_api.endpoints.rest;

import com.fifa_api.models.Post;

import java.util.UUID;

public class PlayerRest {
    private UUID id;
    private String name;
    private Integer number;
    private Post position;
    private String nationality;
    private Integer age;
    private ClubRest club;
}
