package service.required;

import dao.WalletMapper;
import entity.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("requiredServiceB")
public class BService {

    @Autowired
    WalletMapper walletMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public void payoff(){
        Wallet wallet = walletMapper.selectByPrimaryKey("BBB");
        wallet.setMoney(wallet.getMoney()+100);
        walletMapper.updateByPrimaryKey(wallet);
    }


    public void payoff2(){
        Wallet wallet = walletMapper.selectByPrimaryKey("BBB");
        wallet.setMoney(wallet.getMoney()+100);
        walletMapper.updateByPrimaryKey(wallet);
    }
}
