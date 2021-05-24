package com.yzm.annotation.aop;

import org.springframework.stereotype.Service;

@Service
public class AopServiceImpl implements AopService {
    @Override
    public String add() {
        return "添加数据";
    }

    @Override
    public String update() {
        return "修改数据";
    }
}
