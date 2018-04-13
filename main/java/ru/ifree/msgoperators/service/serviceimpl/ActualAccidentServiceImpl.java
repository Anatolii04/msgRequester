package ru.ifree.msgoperators.service.serviceimpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifree.msgoperators.model.ActualAccident;
import ru.ifree.msgoperators.repository.AccidentJdbc;
import ru.ifree.msgoperators.service.ActualAccidentService;

import java.util.List;

@Service
public class ActualAccidentServiceImpl implements ActualAccidentService{

    @Autowired
    AccidentJdbc accidentJdbc;

    @Override
    public List<ActualAccident> getAll() {
        return accidentJdbc.getAll();
    }

    @Override
    public void delete(Integer id) {
        accidentJdbc.delete(id);
    }

    @Override
    public ActualAccident save(ActualAccident actualAccident) {
       return accidentJdbc.save(actualAccident);
    }
}
