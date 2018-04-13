package ru.ifree.msgoperators.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ifree.msgoperators.model.Connector;
import ru.ifree.msgoperators.service.ConnectorService;
import ru.ifree.msgoperators.to.ConnectorTo;
import ru.ifree.msgoperators.util.ToUtil;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ajax/connectors")
public class AjaxConnectorController extends AbstractConnectorController{

    @Autowired
    public AjaxConnectorController(ConnectorService connectorService) {
        super(connectorService);
    }

    @Override
    @GetMapping(value = "/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Connector> getAll(@PathVariable("type")String type) {
        return super.getAll(type);
    }

    @Override
    @GetMapping(value = "/{type}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Connector get(@PathVariable("id") int id,@PathVariable("type")String type) {
        return super.get(id,type);
    }

    @Override
    @DeleteMapping("/{type}/{id}")
    public void delete(@PathVariable("id") int id,@PathVariable("type")String type) {
        super.delete(id,type);
    }

    @PostMapping("/{type}")
    public ResponseEntity<String> createOrUpdate(@PathVariable("type")String type,@Valid ConnectorTo connectorTo,BindingResult result) {
        if(result.hasErrors()){
            StringBuilder sb = new StringBuilder();
            result.getFieldErrors().forEach(fe -> sb.append(fe.getField()).append(" ").append(fe.getDefaultMessage()).append("<br>"));
            return new ResponseEntity<>(sb.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Connector connector = ToUtil.connectorFromTo(connectorTo);
        if (connectorTo.isNew()) {
            super.create(connector,type);
        } else {
            super.update(connector, connector.getId(),type);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
