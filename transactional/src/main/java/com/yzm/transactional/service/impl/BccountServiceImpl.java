package com.yzm.transactional.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzm.transactional.entity.Account;
import com.yzm.transactional.mapper.AccountMapper;
import com.yzm.transactional.service.BccountService;
import com.yzm.transactional.service.CccountService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements BccountService {

    private final CccountService cccountService;

    public BccountServiceImpl(CccountService cccountService) {
        this.cccountService = cccountService;
    }

    @Override
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void methodB12() {
        Account xming = getById(2);
        xming.setMoney(xming.getMoney() + 100);
        updateById(xming);

//        int i = 1 / 0;
    }

    @Override
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void methodB13() {
        Account xming = getById(2);
        xming.setMoney(xming.getMoney() + 100);
        updateById(xming);

        int i = 1 / 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void methodB22() {
        Account xming = getById(2);
        xming.setMoney(xming.getMoney() + 200);
        updateById(xming);

        int i = 1 / 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void methodB32() {
        Account xming = getById(2);
        xming.setMoney(xming.getMoney() + 200);
        updateById(xming);

        int i = 1 / 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void methodB42() {
        Account xming = getById(2);
        xming.setMoney(xming.getMoney() + 100);
        updateById(xming);

        cccountService.methodC44();
        int i = 1 / 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void methodB43() {
        Account xming = getById(2);
        xming.setMoney(xming.getMoney() + 100);
        updateById(xming);

        int i = 1 / 0;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public void methodB52() {
        Account xming = getById(2);
        xming.setMoney(xming.getMoney() + 200);
        updateById(xming);

        int i = 1 / 0;
    }

    @Override
    @Transactional(propagation = Propagation.NEVER, rollbackFor = Exception.class)
    public void methodB62() {
        Account xming = getById(2);
        xming.setMoney(xming.getMoney() + 200);
        updateById(xming);
    }

    @Override
    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public void methodB72() {
        Account xming = getById(2);
        xming.setMoney(xming.getMoney() + 200);
        updateById(xming);

        int i = 1 / 0;
    }

}
