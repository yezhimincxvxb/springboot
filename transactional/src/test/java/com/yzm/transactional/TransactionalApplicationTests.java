package com.yzm.transactional;

import com.yzm.transactional.service.AccountService;
import com.yzm.transactional.service.BccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransactionalApplicationTests {

    @Autowired
    private AccountService accountService;
    @Autowired
    private BccountService bccountService;

    @Test
    void methodA(){
        accountService.methodA1();
    }

    @Test
    void methodA2(){
        accountService.methodA2();
    }

    @Test
    void methodA3(){
        accountService.methodA3();
    }

    @Test
    void methodA4(){
        accountService.methodA4();
    }

    @Test
    void methodA5(){
        accountService.methodA5();
    }

    @Test
    void methodA6(){
        accountService.methodA6();
    }

    @Test
    void methodA7(){
        accountService.methodA7();
    }

}
