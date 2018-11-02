package service.required;

import dao.WalletMapper;
import entity.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("requiredServiceA")
public class AService {

    @Autowired
    private WalletMapper walletMapper;

    @Autowired
    @Qualifier("requiredServiceB")
    private BService bService;


    @Transactional(propagation = Propagation.REQUIRED)
    public void payoff(){
        Wallet wallet = walletMapper.selectByPrimaryKey("AAA");
        wallet.setMoney(wallet.getMoney()+100);
        walletMapper.updateByPrimaryKey(wallet);
        bService.payoff();
    }

    //REQUIRED使得BService.payoff()纳入了AService.payoff()的事务里。
    //当AService.payoff()回滚，BService.payoff()也会被回滚。
    //REQUIRE也是spring声明式事务的默认propagation。
    @Transactional(propagation = Propagation.REQUIRED)
    public void wrongPayoff(){
        Wallet wallet = walletMapper.selectByPrimaryKey("AAA");
        wallet.setMoney(wallet.getMoney()+100);
        walletMapper.updateByPrimaryKey(wallet);
        bService.payoff();
        throw new RuntimeException();
    }

    //wrongPayoff()大致相当于wrongPayoff2()
    @Transactional
    public void wrongPayoff2(){
        Wallet wallet = walletMapper.selectByPrimaryKey("AAA");
        wallet.setMoney(wallet.getMoney()+100);
        walletMapper.updateByPrimaryKey(wallet);
        bService.payoff2();
        throw new RuntimeException();
    }
}
