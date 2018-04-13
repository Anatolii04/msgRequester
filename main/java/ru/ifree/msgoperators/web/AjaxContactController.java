package ru.ifree.msgoperators.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ifree.msgoperators.model.Contact;
import ru.ifree.msgoperators.service.ConnectorService;
import ru.ifree.msgoperators.service.ContactService;
import ru.ifree.msgoperators.to.ContactTo;
import ru.ifree.msgoperators.util.ToUtil;
import ru.ifree.msgoperators.util.exception.NotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ajax/contacts")
public class AjaxContactController extends AbstractContactController {

    @Autowired
    private  ConnectorService connectorService;

    @Autowired
    public AjaxContactController(ContactService contactService) {
        super(contactService);
    }

    @Override
    @GetMapping(value = "/{type}/{connectorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Contact> getAll(@PathVariable("connectorId") int connectorId,@PathVariable("type")String type) {
        return super.getAll(connectorId,type);
    }

    @Override
    @GetMapping(value = "/{type}/{connectorId}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Contact get(@PathVariable("id") int id,@PathVariable("connectorId") int connectorId,@PathVariable("type") String type) {
        return super.get(id,connectorId,type);
    }

    @Override
    @DeleteMapping("/{type}/{connectorId}/{id}")
    public void delete(@PathVariable("id") int id, @PathVariable("connectorId") int connectorId,@PathVariable("type") String type) {
        super.delete(connectorId, id,type);
    }

    @PostMapping(value = "/{type}/{connectorId}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> createOrUpdate(@PathVariable("connectorId") int connectorId,
                                                 @Valid ContactTo contactTo, BindingResult result,@PathVariable(value = "type") String type ){
        contactTo.setCustom(type.equalsIgnoreCase("custom"));
        if(result.hasErrors()){
            StringBuilder sb = new StringBuilder();
            result.getFieldErrors().forEach(fe -> sb.append(fe.getField()).append(" ").append(fe.getDefaultMessage()).append("<br>"));
            return new ResponseEntity<>(sb.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Contact contact = ToUtil.contactFromTo(contactTo);
        if (contact.isNew()) {
            super.create(contact, connectorId);
        } else {
            super.update(contact, contact.getId(), connectorId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/{type}/copy", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> copy(@RequestParam("connectorId") String connectorId, @RequestParam("id") int id,
                                       @RequestParam("from") int from,@PathVariable("type") String type){
        Integer cid;
        Contact contact;
        try{
            cid = Integer.valueOf(connectorId);
            connectorService.get(cid,type.equals("custom"));
            contact = super.get(id,from,type);
        } catch (NotFoundException|NumberFormatException e){
            return new ResponseEntity<>("not found id", HttpStatus.BAD_REQUEST);
        }
        contact.setId(null);
        super.create(contact, cid);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
