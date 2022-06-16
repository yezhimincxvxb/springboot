package com.yzm.transactional.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzm.transactional.entity.Account;
import com.yzm.transactional.mapper.AccountMapper;
import com.yzm.transactional.service.AccountService;
import com.yzm.transactional.service.BccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 账号表 服务实现类
 * </p>
 *
 * @author Yzm
 * @since 2022-03-09
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    private final BccountService bccountService;

    public AccountServiceImpl(BccountService bccountService) {
        this.bccountService = bccountService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void methodA1() {
        Account xwang = getById(1);
        xwang.setMoney(xwang.getMoney() - 200);
        updateById(xwang);

        bccountService.methodB12();
        bccountService.methodB13();
        System.out.println("转账成功！");
//        int i = 1 / 0;
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void methodA2() {
        Account xwang = getById(1);
        xwang.setMoney(xwang.getMoney() - 200);
        updateById(xwang);

        bccountService.methodB22();
        System.out.println("转账成功！");
        int i = 1 / 0;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
    public void methodA3() {
        Account xwang = getById(1);
        xwang.setMoney(xwang.getMoney() - 200);
        updateById(xwang);

        bccountService.methodB32();
        System.out.println("转账成功！");
//        int i = 1 / 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void methodA4() {
        Account xwang = getById(1);
        xwang.setMoney(xwang.getMoney() - 200);
        updateById(xwang);

        bccountService.methodB42();
        System.out.println("转账成功！");
//        int i = 1 / 0;
    }

    @Override
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void methodA5() {
        Account xwang = getById(1);
        xwang.setMoney(xwang.getMoney() - 200);
        updateById(xwang);

        bccountService.methodB52();
        System.out.println("转账成功！");
        int i = 1 / 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void methodA6() {
        Account xwang = getById(1);
        xwang.setMoney(xwang.getMoney() - 200);
        updateById(xwang);

        bccountService.methodB62();
        System.out.println("转账成功！");
    }

    @Override
    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public void methodA7() {
        Account xwang = getById(1);
        xwang.setMoney(xwang.getMoney() - 200);
        updateById(xwang);

        bccountService.methodB72();
        System.out.println("转账成功！");

//        int i = 1 / 0;
    }

}
