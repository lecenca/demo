import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class ConditioanTest {

    @Test
    public void insertTest(){
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(MyConfigue.class);

        User user = (User)context.getBean("userBean");
        try {
            //注入失败，因为条件注入的条件为false，spring容器里没有这个bean
            Wallet wallet = (Wallet)context.getBean("walletBean");
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }
}
