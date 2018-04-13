//package ru.ifree.msgoperators.web;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//import ru.ifree.msgoperators.model.Contact;
//import ru.ifree.msgoperators.service.ContactService;
//
//import java.net.URI;
//import java.util.List;
//
//
//@RestController
//@RequestMapping(value = ContactRestController.CONNECTOR_PATH)
//public class ContactRestController  extends AbstractContactController{
//     static final String CONNECTOR_PATH = "/rest/connector";
//     static final String CONTACTS_PATH = "/contacts";
//
//
//    @Autowired
//    public ContactRestController(ContactService contactService){
//        super(contactService);
//    }
//
//    @Override
//    @GetMapping(value = "/{connectorId}"+ CONTACTS_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<Contact> getAll(@PathVariable("connectorId") int connectorId){
//      return super.getAll(connectorId);
//    }
//
//    @Override
//    @GetMapping(value = "/{connectorId}" + CONTACTS_PATH + "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Contact get(@PathVariable("id") int id, @PathVariable("connectorId") int connectorId) {
//        return super.get(id,connectorId);
//    }
//
//    @PostMapping(value = "/{connectorId}" + CONTACTS_PATH,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Contact> createWithLocation(@RequestBody Contact contact,@PathVariable("connectorId") int connectorId){
//
//        Contact created = super.create(contact,connectorId);
//        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path(CONNECTOR_PATH + "/{connectorId}" + CONTACTS_PATH + "/{id}")
//                .buildAndExpand(connectorId,created.getId()).toUri();
//
//        return ResponseEntity.created(uriOfNewResource).body(created);
//    }
//
//    @DeleteMapping(value = "/{connectorId}" + CONTACTS_PATH + "/{id}")
//    public void delete(@PathVariable("connectorId") int connectorId,@PathVariable("id") int id) {
//        super.delete(id,connectorId);
//    }
//
//    @PutMapping(value = "/{connectorId}" + CONTACTS_PATH +"/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public void update(@RequestBody Contact contact, @PathVariable("id") int id,@PathVariable("connectorId") int connectorId) {
//        super.update(contact,id,connectorId);
//    }
//
//    @GetMapping("/текст")
//    public String text(){
//        return "текст";
//    }
//
//}
