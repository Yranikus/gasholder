package com.example.gasholder.dao;

import com.example.gasholder.entity.Point;

import java.util.List;

public interface DAOInterface<T extends Point> {

    List getAll();
    void save(T t);
    void update(T t);
    void delete(T t);

}
