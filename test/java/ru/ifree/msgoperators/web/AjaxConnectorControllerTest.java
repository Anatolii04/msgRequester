package ru.ifree.msgoperators.web;


import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import ru.ifree.msgoperators.TestUtil;
import ru.ifree.msgoperators.model.Connector;
import ru.ifree.msgoperators.service.ConnectorService;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static ru.ifree.msgoperators.TestData.*;
import static ru.ifree.msgoperators.TestUtil.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-app.xml","classpath:spring/spring-mvc.xml"})
@Sql(scripts = "classpath:db/Populate.sql", config = @SqlConfig(encoding = "UTF-8"))
@WebAppConfiguration
public class AjaxConnectorControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ConnectorService connectorService;

    private MockMvc mockMvc;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception{
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(AJAX_CONNECTORS_CUSTOM))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)))
                .andExpect(content().encoding("UTF-8"));
        TestUtil.print(mockMvc.perform(get(AJAX_CONNECTORS_ESME))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)))
                .andExpect(content().encoding("UTF-8"));
    }

    @Test
    public void testGet() throws Exception{
        ResultActions res  = mockMvc.perform(get(AJAX_CONNECTORS_CUSTOM + ID_MTS))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        Connector returned = readValue(getContent(res), Connector.class);
        assertThat(returned, is(CONNECTOR_MTS));
        ResultActions res1  = mockMvc.perform(get(AJAX_CONNECTORS_ESME + ID_AKOC))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        Connector returned1 = readValue(getContent(res1), Connector.class);
        assertThat(returned1, is(CONNECTOR_AKOC));
    }

    @Test
    @Rollback
    public void testDelete() throws Exception{
        mockMvc.perform(delete(AJAX_CONNECTORS_CUSTOM + ID_MTS))
                .andDo(print())
                .andExpect(status().isOk());
        assertThat(connectorService.getAll(CUSTOM).size(),is(1));
        mockMvc.perform(delete(AJAX_CONNECTORS_ESME + ID_AKOC))
                .andDo(print())
                .andExpect(status().isOk());
        assertThat(connectorService.getAll(ESME).size(),is(302));
        assertThat(connectorService.get(ID_AKOC,ESME).getJurName(), CoreMatchers.equalTo(null));

    }

    @Test
    @Rollback
    public void testCreate() throws Exception {
        mockMvc.perform(post(AJAX_CONNECTORS_CUSTOM)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name",CONNECTOR_T2_CREATE.getName())
                 .param("id","")
                   .param("systemId",CONNECTOR_T2_CREATE.getSystemId())
                       .param("smscAddr",CONNECTOR_T2_CREATE.getSmscAddr())
                        .param("enabled",Boolean.toString(CONNECTOR_T2_CREATE.isEnabled()))
                        .param("jurName",CONNECTOR_T2_CREATE.getJurName())
                        .param("port",Integer.toString(CONNECTOR_T2_CREATE.getPort())))
                .andDo(print())
                .andExpect(status().isCreated());
//        Connector returned = readValue(getContent(res), Connector.class);
//        CONNECTOR_T2_CREATE.setId(returned.getId());
//        assertThat(returned, is(CONNECTOR_T2_CREATE));
       mockMvc.perform(post(AJAX_CONNECTORS_ESME)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id",CONNECTOR_ALTEL.getId()+"")
                .param("name",CONNECTOR_ALTEL.getName())
                .param("systemId",CONNECTOR_ALTEL.getSystemId())
                .param("smscAddr",CONNECTOR_ALTEL.getSmscAddr())
                .param("port",CONNECTOR_ALTEL.getPort()+"")
                .param("enabled",CONNECTOR_ALTEL.isEnabled()+"")
                .param("jurName",CONNECTOR_ALTEL.getJurName()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void testUpdate() throws Exception {
        mockMvc.perform(post(AJAX_CONNECTORS_CUSTOM)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name",CONNECTOR_MTS_UPDATE.getName())
                .param("id",CONNECTOR_MTS_UPDATE.getId()+"")
                .param("systemId",CONNECTOR_MTS_UPDATE.getSystemId())
                .param("smscAddr",CONNECTOR_MTS_UPDATE.getSmscAddr())
                .param("enabled","false")
                .param("jurName",CONNECTOR_MTS_UPDATE.getJurName())
                .param("port","9191"))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(post(AJAX_CONNECTORS_ESME)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id",CONNECTOR_ALTEL.getId()+"")
                .param("name",CONNECTOR_ALTEL.getName())
                .param("systemId",CONNECTOR_ALTEL.getSystemId())
                .param("smscAddr",CONNECTOR_ALTEL.getSmscAddr())
                .param("port",CONNECTOR_ALTEL.getPort()+"")
                .param("enabled",CONNECTOR_ALTEL.isEnabled()+"")
                .param("jurName","i-Free Okraina"))
                .andDo(print())
                .andExpect(status().isOk());

    }

}
