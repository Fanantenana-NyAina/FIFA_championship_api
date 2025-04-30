package com.fifa_api.dao.operations;

import com.fifa_api.dao.DbConnection;
import com.fifa_api.models.Coach;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CoachCRUDOperation implements CRUD<Coach> {
    private final DbConnection datasource;

    @Override
    public List<Coach> getAll(Integer page, Integer size) {
        return List.of();
    }

    @Override
    public Coach getById(UUID id) {
        return null;
    }

    @Override
    public List<Coach> saveAll(List<Coach> entities) {
        return List.of();
    }
}
