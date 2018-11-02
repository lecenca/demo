package service.supports;

import dao.WalletMapper;
import entity.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("supportsServiceB")
public class BService {
    @Autowired
    WalletMapper walletMapper;

    //若外部有事务，则加入其中。若没有，则以非事务方式执行
    @Transactional(propagation = Propagation.SUPPORTS)
    public void payoff(){
        Wallet wallet = walletMapper.selectByPrimaryKey("AAA");
        wallet.setMoney(wallet.getMoney()+100);
        walletMapper.updateByPrimaryKey(wallet);

        wallet = walletMapper.selectByPrimaryKey("BBB");
        wallet.setMoney(wallet.getMoney()+100);
        walletMapper.updateByPrimaryKey(wallet);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void wrongPayoff(){
        Wallet wallet = walletMapper.selectByPrimaryKey("AAA");
        wallet.setMoney(wallet.getMoney()+100);
        walletMapper.updateByPrimaryKey(wallet);

        walletMapper.insert(wallet);//重复插入，抛出异常

        wallet = walletMapper.selectByPrimaryKey("BBB");
        wallet.setMoney(wallet.getMoney()+100);
        walletMapper.updateByPrimaryKey(wallet);
    }
}
