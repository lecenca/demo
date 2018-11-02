package service.requiresNew;

import dao.WalletMapper;
import entity.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("requiresNewServiceA")
public class AService {
    @Autowired
    private WalletMapper walletMapper;

    @Autowired
    @Qualifier("requiresNewServiceB")
    private BService bService;


    @Transactional(propagation = Propagation.REQUIRED)
    public void payoff(){
        Wallet wallet = walletMapper.selectByPrimaryKey("AAA");
        wallet.setMoney(wallet.getMoney()+100);
        walletMapper.updateByPrimaryKey(wallet);
        bService.payoff();
    }

    //REQUIRES_NEW使得AService.payoff()与BService.payoff()的事务独立。
    //当AService.payoff()回滚，BService.payoff()不会被回滚。
    @Transactional(propagation = Propagation.REQUIRED)
    public void wrongPayoff(){
        Wallet wallet = walletMapper.selectByPrimaryKey("AAA");
        wallet.setMoney(wallet.getMoney()+100);
        walletMapper.updateByPrimaryKey(wallet);
        bService.payoff();
        throw new RuntimeException();
    }
}
