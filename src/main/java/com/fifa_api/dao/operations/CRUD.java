package com.fifa_api.dao.operations;

import java.util.List;
import java.util.UUID;

public interface CRUD<E> {
    List<E> getAll(Integer page, Integer size);

    E getById(UUID id);

    List<E> saveAll(List<E> entities);
}
