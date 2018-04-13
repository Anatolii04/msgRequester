package ru.ifree.msgoperators.service;

import org.hamcrest.core.IsCollectionContaining;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ifree.msgoperators.TestData;
import ru.ifree.msgoperators.model.Contact;
import ru.ifree.msgoperators.util.exception.NotFoundException;


import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@ContextConfiguration({
        "classpath:spring/spring-app.xml"
})
@Sql(scripts = "classpath:db/Populate.sql", config = @SqlConfig(encoding = "UTF-8"))
@RunWith(SpringRunner.class)
public class ContactServiceTest {

    @Autowired
    ContactService contactService;

    private static final Logger LOG = LoggerFactory.getLogger(Contact.class);
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
        Contact custom  = contactService.save(TestData.CONTACT_MTS_NEW, TestData.ID_MTS);
        Contact esme = contactService.save(TestData.CONTACT_AKOC_NEW, TestData.ID_AKOC);
        assertThat(custom,is(contactService.get(custom.getId(), TestData.ID_MTS, TestData.CUSTOM)));
        assertThat(esme,is(contactService.get(esme.getId(), TestData.ID_AKOC, TestData.ESME)));
    }

    @Test
    @Transactional
    @Rollback
    public void testDelete() throws Exception{
        contactService.delete(TestData.CONTACT_MTS_ID, TestData.ID_MTS,TestData.CUSTOM);
        contactService.delete(TestData.CONTACT_A_MOBILE_ID, TestData.ID_A_MOBILE,TestData.ESME);
        assertThat(contactService.getAll(TestData.ID_MTS,TestData.CUSTOM),not(IsCollectionContaining.hasItem(TestData.CONTACT_MTS)));
        assertThat(contactService.getAll(TestData.ID_A_MOBILE,TestData.ESME),not(IsCollectionContaining.hasItem(TestData.CONTACT_AKOC)));
    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteNotFound() throws Exception{
        thrown.expect(NotFoundException.class);
        contactService.delete(TestData.ID_GHOST, TestData.ID_AKOC,TestData.ESME);
        contactService.delete(TestData.ID_GHOST, TestData.ID_MTS,TestData.CUSTOM);

    }

    @Test
    @Transactional
    @Rollback
    public void testGet() throws Exception{
        Contact custom = contactService.get(TestData.CONTACT_MTS_ID, TestData.ID_MTS,TestData.CUSTOM);
        Contact esme = contactService.get(TestData.CONTACT_A_MOBILE_ID, TestData.ID_A_MOBILE,TestData.ESME);
        Assert.assertEquals(TestData.CONTACT_MTS,custom);
        Assert.assertEquals(TestData.CONTACT_A_MOBILE,esme);
    }

    @Test
    @Transactional
    @Rollback
    public void testGetNotFound() throws Exception{
        thrown.expect(NotFoundException.class);
        contactService.get(TestData.ID_GHOST, TestData.ID_MTS,TestData.CUSTOM);
        contactService.get(TestData.ID_GHOST, TestData.ID_AKOC,TestData.ESME);
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdate() throws Exception{
        Contact contact0 = new Contact(TestData.CONTACT_MTS);
        contact0.setDescription("BLA");
        Contact contact = contactService.update(contact0, TestData.ID_MTS);
        assertEquals(contact0,contact);
        Contact esme = contactService.update(TestData.CONTACT_AKOC_UPDATE,TestData.ID_AKOC);
        assertEquals(TestData.CONTACT_AKOC_UPDATE,esme);
    }

    @Test
    @Transactional
    @Rollback
    public void testUpdateNotFound() throws Exception{
        thrown.expect(NotFoundException.class);
        Contact contact = new Contact(TestData.CONTACT_MTS);
        contact.setId(159);
        contactService.update(TestData.CONTACT_MTS, TestData.ID_GHOST);
    }

    @Test
    @Rollback
    @Transactional(propagation= Propagation.REQUIRED)
    public void testGetAll() throws Exception{
        Assert.assertEquals(1,contactService.getAll(TestData.ID_MTS,TestData.CUSTOM).size());
        Assert.assertEquals(1,contactService.getAll(TestData.ID_AKOC,TestData.ESME).size());
    }

}
