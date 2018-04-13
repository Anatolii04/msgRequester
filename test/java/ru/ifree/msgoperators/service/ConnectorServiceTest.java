package ru.ifree.msgoperators.service;



import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.ifree.msgoperators.TestData;
import ru.ifree.msgoperators.util.exception.NotFoundException;
import ru.ifree.msgoperators.model.Connector;


import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


import java.util.concurrent.TimeUnit;


@ContextConfiguration({
        "classpath:spring/spring-app.xml"
})
@Sql(scripts = "classpath:db/Populate.sql", config = @SqlConfig(encoding = "UTF-8"))
@RunWith(SpringRunner.class)
public class ConnectorServiceTest {

    @Autowired
    ConnectorService connectorService;

    private static final Logger LOG = LoggerFactory.getLogger(Connector.class);
    private static StringBuilder results = new StringBuilder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("%-25s %7d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result).append('\n');
            LOG.info(result + " ms\n");
        }
    };
    @AfterClass
    public static void printResult() {
        LOG.info("\n---------------------------------" +
                "\nTest                 Duration, ms" +
                "\n---------------------------------\n" +
                results +
                "---------------------------------\n");
    }




    @Test
    @Transactional
    @Rollback
    public void testSaveConnector() throws Exception{
        Connector custom = TestData.createConnector();
        Connector esme = TestData.createConnector();
        esme.setJurName("China");
        Connector customRes = connectorService.save(custom,TestData.CUSTOM);
        Connector esmeRes = connectorService.save(esme,TestData.ESME);
        assertThat(customRes,is(custom));
        assertThat(esmeRes,is(esme));
    }

    @Test
    @Transactional
    @Rollback
    public void testDelete() throws Exception{
        connectorService.delete(TestData.ID_MTS,TestData.CUSTOM);
        connectorService.delete(TestData.ID_AKOC,TestData.ESME);
        assertThat(connectorService.getAll(TestData.CUSTOM),not(CoreMatchers.hasItem(TestData.CONNECTOR_MTS)));
        assertThat(connectorService.getAll(TestData.ESME),CoreMatchers.hasItem(TestData.CONNECTOR_AKOC));
        assertThat(connectorService.get(TestData.ID_AKOC,TestData.ESME).getJurName(), CoreMatchers.equalTo(null));
    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteNotFound() throws Exception{
        thrown.expect(NotFoundException.class);
        connectorService.delete(TestData.ID_GHOST,TestData.ESME);
        connectorService.delete(TestData.ID_GHOST,TestData.CUSTOM);

    }

    @Test
    @Transactional
    @Rollback
    public void testGet() throws Exception{
        Connector customRes = connectorService.get(TestData.ID_MTS,TestData.CUSTOM);
        Connector esmeRes = connectorService.get(TestData.ID_AKOC,TestData.ESME);
        Assert.assertEquals(TestData.CONNECTOR_MTS,customRes);
        Assert.assertEquals(TestData.CONNECTOR_AKOC,esmeRes);
    }

    @Test
    @Transactional
    @Rollback
    public void tesGetNotFound() throws Exception{
        thrown.expect(NotFoundException.class);
        connectorService.get(TestData.ID_GHOST,TestData.CUSTOM);
        connectorService.get(TestData.ID_GHOST,TestData.ESME);
    }
//
    @Test
    @Transactional
    @Rollback
    public void testUpdate() throws Exception{
        Connector custom = connectorService.save(TestData.CONNECTOR_MTS_UPDATE,TestData.CUSTOM);
        Connector akoc = TestData.CONNECTOR_AKOC;
        akoc.setName("AKOS");
        akoc.setJurName("China");
        Connector esme = connectorService.save(TestData.CONNECTOR_AKOC,TestData.ESME);
        assertEquals(custom,connectorService.get(TestData.ID_MTS,TestData.CUSTOM));
        assertEquals(esme,connectorService.get(TestData.ID_AKOC,TestData.ESME));
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateNotFound() throws Exception{
        thrown.expect(NotFoundException.class);
        connectorService.update(TestData.CONNECTOR_GHOST,TestData.ESME);
        connectorService.update(TestData.CONNECTOR_GHOST,TestData.CUSTOM);

    }

    @Test
    @Transactional
    @Rollback
    public void testGetAll() throws Exception{
        Assert.assertEquals(TestData.connectorsDb(),connectorService.getAll(TestData.CUSTOM));
        Assert.assertEquals(302,connectorService.getAll(TestData.ESME).size());
    }
}
