package util;

import dao.WalletMapper;
import entity.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class Display {

    @Autowired
    private WalletMapper walletMapper;

    public <T> void payoff(T t,Consumer<T> foo){
        Wallet walletA = walletMapper.selectByPrimaryKey("AAA");
        Wallet walletB = walletMapper.selectByPrimaryKey("BBB");
        System.out.println("before");
        System.out.println("AAA :"+walletA.getMoney());
        System.out.println("BBB :"+walletB.getMoney());

        try {
            foo.accept(t);
        } catch (Exception e) {

        }

        walletA = walletMapper.selectByPrimaryKey("AAA");
        walletB = walletMapper.selectByPrimaryKey("BBB");
        System.out.println("after");
        System.out.println("AAA :"+walletA.getMoney());
        System.out.println("BBB :"+walletB.getMoney());
    }
}
