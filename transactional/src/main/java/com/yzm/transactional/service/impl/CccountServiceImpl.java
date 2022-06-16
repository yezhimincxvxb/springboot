package com.yzm.transactional.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzm.transactional.entity.Account;
import com.yzm.transactional.mapper.AccountMapper;
import com.yzm.transactional.service.BccountService;
import com.yzm.transactional.service.CccountService;
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
public class CccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements CccountService {

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void methodC44() {
        Account xming = getById(3);
        xming.setMoney(xming.getMoney() + 100);
        updateById(xming);

//        int i = 1 / 0;
    }

}
