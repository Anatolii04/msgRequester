import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.mail.MailSender;
import ru.ifree.msgoperators.model.EsmeJurName;
import ru.ifree.msgoperators.repository.Jdbc.EsmeJurNamesRepository;

import java.util.List;
import java.util.Map;


public class MAIN {


    public static void main(String[] args) {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
//            System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()));
//            JpaContactRepoImpl crjdbc = appCtx.getBean(JpaContactRepoImpl.class);
//            JpaConnectorRepoImpl jpaconrepo = appCtx.getBean(JpaConnectorRepoImpl.class);
////            crjdbc.delete(1);
////            crjdbc.save(new Contact("123","asd"),1);
//            jpaconrepo.get(1).getContacts().forEach(e-> System.out.println(e));
//
////            List<Connector> list = crjdbc.getAll();
////            list.stream().forEach(e -> System.out.println(e));
//            new String().equals(null);
//              MailSender ms = appCtx.getBean(MailSender.class);
//            JdbcTemplate jt = appCtx.getBean(JdbcTemplate.class);
//            NamedParameterJdbcTemplate nt = appCtx.getBean(NamedParameterJdbcTemplate.class);
//            DataSource ds = appCtx.getBean(DataSource.class);
//            EsmeJurNamesRepository repo = new EsmeJurNamesRepository(ds,jt);
//            List<EsmeJurName> m =repo.getAllEsmeJurNames();
//            for (int i = 0; i < m.size(); i++) {
//                System.out.println(m.get(i).toString());
//            }
//            EsmeJurName e= repo.get(123);
//
        }

    }
}
