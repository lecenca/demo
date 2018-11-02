import dao.WalletMapper;
import entity.Wallet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.requiresNew.AService;
import util.Display;

import java.util.function.Consumer;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-dao.xml"})
public class RequiresNewTest {

    @Autowired
    private Display display;

    @Autowired
    @Qualifier("requiresNewServiceA")
    private AService aService;

    @Test
    public void test001(){
        //AAA、BBB的工资都加100
        display.payoff(aService, AService::payoff);
    }

    @Test
    public void test002(){
        //AAA的工资都不变
        //BBB的工资正常增加100
        display.payoff(aService, AService::wrongPayoff);
    }
}
