package ru.ifree.msgoperators.web;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;
import ru.ifree.msgoperators.TestUtil;
import ru.ifree.msgoperators.model.Contact;
import ru.ifree.msgoperators.service.ContactService;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static ru.ifree.msgoperators.TestData.*;
import static ru.ifree.msgoperators.TestUtil.getContent;
import static ru.ifree.msgoperators.TestUtil.readValue;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-app.xml","classpath:spring/spring-mvc.xml"})
@Sql(scripts = "classpath:db/Populate.sql", config = @SqlConfig(encoding = "UTF-8"))
@WebAppConfiguration
public class AjaxContactControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ContactService contactService;

    private MockMvc mockMvc;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception{
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAll() throws Exception{
        TestUtil.print(mockMvc.perform(get(AJAX_CONTACT_CUSTOM  + ID_MTS))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)))
                .andExpect(content().encoding("UTF-8"));
        TestUtil.print(mockMvc.perform(get(AJAX_CONTACT_ESME  + ID_MTS))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)))
                .andExpect(content().encoding("UTF-8"));
        assertThat(contactService.getAll(ID_MTS,CUSTOM).size(),is(1));
        assertThat(contactService.getAll(ID_AKOC,ESME).size(),is(1));
    }

    @Test
    public void testGet() throws Exception{
        ResultActions res  = mockMvc.perform(get(AJAX_CONTACT_CUSTOM + ID_MTS  + "/" + CONTACT_MTS.getId()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        Contact returned = readValue(getContent(res), Contact.class);
        assertThat(returned, is(CONTACT_MTS));
        ResultActions res1  = mockMvc.perform(get(AJAX_CONTACT_ESME + ID_AKOC  + "/" + CONTACT_AKOC.getId()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        Contact returned1 = readValue(getContent(res1), Contact.class);
        assertThat(returned1, is(CONTACT_AKOC));
    }

    @Test
    @Rollback
    public void testDelete() throws Exception{
        mockMvc.perform(delete(AJAX_CONTACT_CUSTOM + ID_MTS  +  "/" + CONTACT_MTS.getId()))
                .andDo(print())
                .andExpect(status().isOk());
        assertThat(contactService.getAll(ID_MTS,CUSTOM).size(),is(0));
        mockMvc.perform(delete(AJAX_CONTACT_ESME + ID_AKOC  + "/" + CONTACT_AKOC.getId()))
                .andDo(print())
                .andExpect(status().isOk());
        assertThat(contactService.getAll(ID_AKOC,ESME).size(),is(0));

    }

    @Test
    @Rollback
    public void testCreate() throws Exception {
        mockMvc.perform(post(AJAX_CONTACT_CUSTOM  + ID_MTS )
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("contact","7986543211")
                .param("description","blablaba")
        )
                .andExpect(status().isCreated());
        mockMvc.perform(post(AJAX_CONTACT_ESME  + ID_AKOC )
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("contact","7986543211")
                .param("description","blablaba esme")
        )
                .andExpect(status().isCreated());
        assertThat(contactService.getAll(ID_MTS,CUSTOM).size(),is(2));
        assertThat(contactService.getAll(ID_AKOC,ESME).size(),is(2));
    }

    @Test
    @Rollback
    public void testUpdate() throws Exception {
        mockMvc.perform(post(AJAX_CONTACT_CUSTOM + ID_MTS )
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("id",CONTACT_MTS_UPDATE.getId()+"")
                .param("contact",CONTACT_MTS_UPDATE.getContact()+"")
                .param("description",CONTACT_MTS_UPDATE.getDescription()))
                .andDo(print())
                .andExpect(status().isOk());
        assertThat(contactService.get(CONTACT_MTS_ID,ID_MTS,CUSTOM), is(CONTACT_MTS_UPDATE));
        mockMvc.perform(post(AJAX_CONTACT_ESME + ID_A_MOBILE )
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("id",CONTACT_A_MOBILE.getId()+"")
                .param("contact","000000")
                .param("description","new descr"))
                .andDo(print())
                .andExpect(status().isOk());
        CONTACT_A_MOBILE.setDescription("000000");
        CONTACT_A_MOBILE.setContact("new descr");
        assertThat(contactService.get(CONTACT_A_MOBILE.getId(),ID_A_MOBILE,ESME), is(CONTACT_A_MOBILE));
    }

//    @Test
//    public void testCreateNotNull() throws Exception{
//        thrown.expect(IllegalArgumentException.class);
//        try{
//            mockMvc.perform(post(CONNECTOR_PATH + "/" + ID_MTS + CONTACTS_PATH)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(writeValue(CONTACT_MF)));}
//        catch (NestedServletException e){
//            throw (IllegalArgumentException) e.getCause();
//        }
//    }
}
