import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.supports.AService;
import util.Display;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-dao.xml"})
public class SupportsTest {
    @Autowired
    private Display display;

    @Autowired
    @Qualifier("supportsServiceA")
    private AService aService;

    @Test
    public void test001(){
        //AAA、BBB的工资都加100
        display.payoff(aService, AService::payoff);
    }

    @Test
    public void test002(){
        //AAA、BBB的工资都不变
        display.payoff(aService, AService::wrongPayoff);
    }

    @Test
    public void test003(){
        //由于没有事务，BService::wrongPayoff不会回滚
        //AAA工资增加100
        //BBB工资不变
        display.payoff(aService, AService::wrongPayoff2);
    }
}
