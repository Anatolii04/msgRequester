package ru.ifree.msgoperators.web;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-app.xml","classpath:spring/spring-mvc.xml"})
@WebAppConfiguration
public class RootControllerTest {

//    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new RootController()).build();

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception{
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }




    @Test
    public void testGetRoot() throws Exception{
         mockMvc.perform(get("/")).andExpect(status().isMovedTemporarily());
    }
    @Test
    public void testGetCustom() throws Exception{
         mockMvc.perform(get("/custom")).andExpect(status().isOk());
    }
}
