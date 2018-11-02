package service.supports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("supportsServiceA")
public class AService {

    @Autowired
    @Qualifier("supportsServiceB")
    private BService bService;

    @Transactional
    public void payoff(){
        bService.payoff();
    }

    @Transactional
    public void wrongPayoff(){
        bService.wrongPayoff();
    }

    //没有事务
    public void wrongPayoff2(){
        bService.wrongPayoff();
    }
}
