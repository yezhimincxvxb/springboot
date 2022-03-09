package com.yzm.transactional.service.impl;

import com.yzm.transactional.entity.Account;
import com.yzm.transactional.mapper.AccountMapper;
import com.yzm.transactional.service.AccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void method01() {
        Account xwang = getById(1);
        xwang.setMoney(xwang.getMoney() - 200);
        updateById(xwang);

        this.method01_2();
        System.out.println("小王金额：" + getById(1).getMoney());
        System.out.println("小明金额：" + getById(2).getMoney());

//        int i = 1 / 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void method01_2() {
        Account xming = getById(2);
        xming.setMoney(xming.getMoney() + 200);
        updateById(xming);

//        int i = 1 / 0;
    }

}
