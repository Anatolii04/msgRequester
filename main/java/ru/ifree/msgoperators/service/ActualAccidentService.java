package ru.ifree.msgoperators.service;


import ru.ifree.msgoperators.model.ActualAccident;

import java.util.List;

public interface ActualAccidentService {
    List<ActualAccident> getAll();
    void delete(Integer id);
    ActualAccident save(ActualAccident actualAccident);
}
