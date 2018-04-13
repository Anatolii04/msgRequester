package ru.ifree.msgoperators.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import ru.ifree.msgoperators.model.ActualAccident;
import ru.ifree.msgoperators.model.Connector;
import ru.ifree.msgoperators.model.Contact;
import ru.ifree.msgoperators.repository.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component("zabbixApiScheduller")
public class Scheduller {

    @Autowired
    @Qualifier(value = "esmeConnectorRepositoryJdbcImpl")
    ConnectorRepository esmeConnectorRepository;
    @Autowired
    @Qualifier(value = "esmeContactRepositoryJdbcImpl")
    ContactRepository esmeContactRepositoryJdbcImpl;

    @Autowired
    AccidentJdbc accidentJdbc;

    @Autowired
    MailSender mailSender;

    public void scheduleFixedDelayTask() throws IOException {
        System.out.println(
                "Fixed delay task - " + System.currentTimeMillis() / 1000);
        accidentsChecker();
    }

//    @EventListener(ContextRefreshedEvent.class)
//    @PostConstruct
    public void accidentsChecker() throws IOException{
        String url = "https://zabbix.i-free.ru/api_jsonrpc.php";
        String authJson = "{" +
                "\"jsonrpc\": \"2.0\"," +
                "\"method\": \"user.login\"," +
                "\"params\": {" +
                 "\"user\": \"a.utkin\"," +
                    "\"password\": \"{}\"},\"id\":1}";

//        System.out.println("run auth req");
//        String authKey = getNode(postRequestToZabbixApi(url,authJson)).get("result").toString();

//        String key = jsonNode.get("result");
//        Path path = Paths.get("/tmp/key.tmp");
//        byte[] strToBytes = jsonNode.get("result").toString().replaceAll("\"","").getBytes();
//        Files.write(path, strToBytes);
//        client.close();
//        String triggerJson = String.format("{\"jsonrpc\": \"2.0\", \"method\": \"trigger.get\", \"params\": {\"search\":" +
//                "{\"description\": \"Node is not connected or flapping\"}, \"expandData\": \"\", \"filter\": {\"value\": 1}}," +
//                "\"auth\": %s, \"id\": 1 }", authKey);
//        System.out.println("run triggers req");
//        JSONArray arrayOfZabbixNodes = new JSONArray(getNode(postRequestToZabbixApi(url,triggerJson)).get("result").toString());
        String[] testArrayOfZabbixNodes = new String[]{"MSG3_Interactive.ilan", "MSG3_Spam.NovaOTP", "MSG3_Lime.GEBANK_Comex_ProdSMS_1", "MSG3_Lime.Fasten", "MSG3_Lime.SKB-site_Lime, MSG3_Interactive.playfon_ua", "MSG3_Interactive.Altel"};
        List<String> problemNodes = new ArrayList<>();
        for (int i = 0; i < testArrayOfZabbixNodes.length; i++) {
            SimpleMailMessage emailToOperator = new SimpleMailMessage();
//            String nodeName = arrayOfZabbixNodes.getJSONObject(i).getString("host");
            String nodeName = testArrayOfZabbixNodes[i];
            String node = nodeName.split("\\.")[1];
            List<ActualAccident> actualAccidents = accidentJdbc.get(node).stream().filter(n->n.getActual().equals(true)).collect(Collectors.toList());
            System.out.println(actualAccidents);
            if(nodeName.matches("MSG3_Interactive.*") && actualAccidents.isEmpty()){
                problemNodes.add(nodeName);
                Integer id = 1448;//esmeConnectorRepository.getId(nodeName);
                if(id.equals(1448)){
                    List<Contact> nodeContacts = esmeContactRepositoryJdbcImpl.getAll(1448,false);
                    if(nodeContacts!=null && !nodeContacts.isEmpty()){
                        List<Contact> emailContacts = nodeContacts.stream().filter(e -> e.getContact().contains("@")).collect(Collectors.toList());
                        String[] recipientContacts = new String[emailContacts.size()];
                        for (int j = 0; j < emailContacts.size(); j++) {
                            recipientContacts[j] = emailContacts.get(j).getContact();
                        }

                        emailToOperator.setFrom("lifter04a@gmail.com");
                        emailToOperator.setTo(recipientContacts);
                        Connector connector = emailContacts.get(0).getConnector();
                        emailToOperator.setSubject("Запрос от группы компаний " + connector.getJurName());
                        emailToOperator.setText("Здравствуйте!\n" +
                                "\n" +
                                "Фиксируем отсутствие соединения с Вашим SMSC от [dd MMM hh:mm].\n" +
                                "С нашей стороны проблем не обнаружено. Пожалуйста, проверьте работоспособность на вашей стороне.\n\n" +
                                "Отладочная информация:\n" +
                                "smscAddr=" + connector.getSmscAddr() +
                        "\nport=" + connector.getPort() +
                        "\nsystemId=" + connector.getSystemId() +
                        "\nС уважением,\n" +
                                "i-Free, Служба мониторинга и технической поддержки\n" +
                                "+7 911 100-14-57\n" +
                                "techsupp@i-free.com");
//                        mailSender.send(emailToOperator);
                        accidentJdbc.save(new ActualAccident(null,nodeName.split
                                ("\\.")[1],new Timestamp(new Date().getTime()),new Boolean(true)));
                        System.out.println(new Timestamp(new Date().getTime()).getTime());
                    }
                }


            }

        }
        System.out.println(problemNodes);
    }

    public HttpEntity postRequestToZabbixApi(String url, String json) throws IOException{
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(json);
        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Accept","application/json");
        httpPost.setHeader("Content-Type","application/json");
        CloseableHttpResponse response = client.execute(httpPost);
        return response.getEntity();
    }

    public JsonNode getNode(HttpEntity json) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(EntityUtils.toString(json));
        return jsonNode;
    }
}
