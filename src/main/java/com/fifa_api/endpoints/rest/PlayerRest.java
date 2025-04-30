package com.fifa_api.endpoints.rest;

import com.fifa_api.models.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlayerRest {
    private UUID id;
    private String name;
    private Integer number;
    private Post position;
    private String nationality;
    private Integer age;
    private ClubRest club;
}
