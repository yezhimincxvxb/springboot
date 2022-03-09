package com.yzm.transactional;

import com.yzm.transactional.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransactionalApplicationTests {

    @Autowired
    private AccountService accountService;

    @Test
    void contextLoads() {
        accountService.method01();
    }

}
