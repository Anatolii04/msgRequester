package ru.ifree.msgoperators.repository;


import ru.ifree.msgoperators.model.ActualAccident;

import java.util.List;

public interface AccidentJdbc {
    Boolean delete(Integer id);
    ActualAccident save(ActualAccident actualAccident);
    List<ActualAccident> get(String name);
    List<ActualAccident> getAll();

}
