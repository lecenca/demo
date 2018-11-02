package service.requiresNew;

import dao.WalletMapper;
import entity.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("requiresNewServiceB")
public class BService {
    @Autowired
    WalletMapper walletMapper;

    //为BService.payoff()开启一个新事务
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void payoff(){
        Wallet wallet = walletMapper.selectByPrimaryKey("BBB");
        wallet.setMoney(wallet.getMoney()+100);
        walletMapper.updateByPrimaryKey(wallet);
    }
}
