package ru.ifree.msgoperators.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ifree.msgoperators.model.ActualAccident;
import ru.ifree.msgoperators.service.ActualAccidentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ajax/accidents")
public class AjaxActualAccidentController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    ActualAccidentService accidentService;

    @GetMapping
    public List<ActualAccident> getAll(){
        return accidentService.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        LOGGER.info("delete accident");
        accidentService.delete(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> update(ActualAccident actualAccident, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            bindingResult.getFieldErrors().forEach(fe -> sb.append(fe.getField()).append(" ").append(fe.getDefaultMessage()).append("<br>"));
            return new ResponseEntity<>(sb.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if(actualAccident.getId()!=null){
            accidentService.save(actualAccident);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
