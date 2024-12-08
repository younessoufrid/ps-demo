package com.demo.portailsaisie.backend.core.service.interfaces;

public interface GenericService<T> {
    public T save(T entity);

    public T saveIfNotExist(T entity);
}
