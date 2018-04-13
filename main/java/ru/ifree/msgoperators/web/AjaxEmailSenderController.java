package ru.ifree.msgoperators.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import ru.ifree.msgoperators.model.Connector;
import ru.ifree.msgoperators.model.Contact;
import ru.ifree.msgoperators.service.ContactService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ajax/email")
@PropertySource("classpath:email/email.properties")
public class AjaxEmailSenderController {
    @Value("${user.sender}")
    private String userSender;

    private static final Logger LOGGER = LoggerFactory.getLogger(AjaxEmailSenderController.class);

    @Autowired
    private MailSender mailSender;

    @Autowired
    private ContactService contactService;


    @PostMapping(value= "/{type}/data/{connectorId}")
    public ResponseEntity<String> consumeEmails(@RequestParam Map<Integer,String> mapEmails, @PathVariable("connectorId") Integer id,@PathVariable("type") String type){
        Boolean isCustom = type.equalsIgnoreCase("custom");
        ObjectMapper mapper = new ObjectMapper();
        List<Connector> result = new ArrayList<>();
        LOGGER.info("consumeEmails [{}] connector [{}]", mapEmails.entrySet().stream().map(e -> e.getValue()).collect(Collectors.joining(", ")),id);
        if(mapEmails.size()!=0) {
            List<Contact> contactsList = contactService.getWhole(isCustom);
            for (Map.Entry<Integer, String> m :
                    mapEmails.entrySet()) {
                for (int i = 0; i < contactsList.size(); i++) {
                    Contact c = contactsList.get(i);
                    String contact = c.getContact();
                    if(contact.contains("@")){
                        if(m.getValue().equals(contact)){
                            Connector connector = c.getConnector();
                            List<String> list = result.stream().map(Connector::getName).collect(Collectors.toList());
                            if(!list.contains(connector.getName())){
                                //flash contacts for json
                                connector.setContacts(null);
                                result.add(connector);
                            }
                        }
                    }

                }
            }
        }else{
            return new ResponseEntity<>("No contacts",HttpStatus.BAD_REQUEST);
        }
        String attributes;
        try{
             attributes = mapper.writeValueAsString(result);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }

    @PostMapping(value = "/send")
    public ResponseEntity<String> emailSend(HttpServletRequest request) {
        final String EMAILREGEX = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        final String DELIMREGEX = ";";
        List<String> copyList = new ArrayList<>();
        List<String> recipList = new ArrayList<>();
        List<String> notValidEmails = new ArrayList<>();
        // creates a simple e-mail object
        SimpleMailMessage emailToOperator = new SimpleMailMessage();

        String recipient = request.getParameter("recipient").replaceAll(" ","");
        String inCopy = request.getParameter("copy").replaceAll(" ","");
        String[] recipients = recipient.split(DELIMREGEX);
        String[] copy = inCopy.split(DELIMREGEX);
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");
        LOGGER.info("emailSend recipients: {} copy: {} subject: [{}] message: {}",
                Arrays.toString(recipients),Arrays.toString(copy), subject, message.replaceAll("\\n"," "));
        if(recipient.equals("")){
            return new ResponseEntity<>("No recipients", HttpStatus.BAD_REQUEST);
        }
        for (int i = 0; i < recipients.length; i++) {
            String recipMail = recipients[i];
            if(recipMail.matches(EMAILREGEX)){
                recipList.add(recipMail);
            } else notValidEmails.add(recipients[i]);
        }
        if(!inCopy.equals("")) {
            for (int i = 0; i < copy.length; i++) {
                String copyMail = copy[i];
                if (copyMail.matches(EMAILREGEX)) {
                    copyList.add(copyMail);
                } else notValidEmails.add(copy[i]);
            }
        }

        if(notValidEmails.size()!=0){
            String notValid = notValidEmails.stream().collect(Collectors.joining(", "));
            return new ResponseEntity<>(notValid ,HttpStatus.BAD_REQUEST);
        }

        emailToOperator.setFrom(userSender);
        emailToOperator.setTo(recipList.stream().toArray(String[]::new));
        emailToOperator.setCc(copyList.stream().toArray(String[]::new));
        emailToOperator.setSubject(subject);
        emailToOperator.setText(message);
        // sends the e-mail
        mailSender.send(emailToOperator);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
