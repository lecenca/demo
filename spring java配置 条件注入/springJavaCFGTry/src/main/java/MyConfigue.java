import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfigue {

    @Bean
    @Conditional(UserCondition.class)
    public User userBean(){
        User user = new User();
        user.setName("John");
        return user;
    }

    @Bean
    @Conditional(WalletCondition.class)
    public Wallet walletBean(){
        Wallet wallet = new Wallet();
        wallet.setName("John");
        wallet.setMoney(100);
        return wallet;
    }
}
