//package ru.ifree.msgoperators.web;
//
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//import ru.ifree.msgoperators.model.Connector;
//import ru.ifree.msgoperators.service.ConnectorService;
//
//
//import java.net.URI;
//import java.util.List;
//
//
//
//@RestController
//@RequestMapping(ConnectorRestController.CONNECTORS_PATH)
//public class ConnectorRestController extends AbstractConnectorController{
//    static final String CONNECTORS_PATH = "/rest/connectors";
//
//    @Autowired
//    public ConnectorRestController(ConnectorService connectorService){
//        super(connectorService);
//    }
//
//
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<Connector> getAll() {
//        return super.getAll();
//    }
//
//    @Override
//    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Connector get(@PathVariable("id") int id) {
//        return super.get(id);
//    }
//
//
//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Connector> createWithLocation(@RequestBody Connector connector) {
//        Connector created = super.create(connector);
//        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path(CONNECTORS_PATH + "/{id}")
//                .buildAndExpand(created.getId()).toUri();
//
//        return ResponseEntity.created(uriOfNewResource).body(created);
//    }
//
//    @Override
//    @DeleteMapping(value = "/{id}")
//    public void delete(@PathVariable("id") int id) {
//        super.delete(id);
//    }
//
//    @Override
//    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public void update(@RequestBody Connector connector, @PathVariable("id") int id) {
//        super.update(connector,id);
//    }
//
////    @RequestMapping(value = "/save", method = RequestMethod.POST)
////    public String saveOrUpdate(Connector connector){
////        if(connector.isNew()){
////            LOGGER.info("save {}", connector);
////            connectorService.save(connector);
////        }
////        else{
////            LOGGER.info("update {}", connector);
////            connectorService.update(connector);
////        }
////        return "redirect:/operators";
////    }
//
////
////    @RequestMapping(value = "/get")
////    @ResponseBody
////    public Connector get(
////            @RequestParam("id") Integer id
////    ){
////        LOGGER.info("get {}", id);
////        Connector connector = connectorService.get(id);
////        return connector;
////    }
//
////    @RequestMapping(value = "/delete")
////    public String delete(
////            @RequestParam("id") Integer id
////    ){
////        LOGGER.info("delete {}", id);
////        connectorService.delete(id);
////        return "redirect:/operators";
////    }
//
//
//}
