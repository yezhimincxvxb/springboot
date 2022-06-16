package com.yzm.thread.scheduled;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;

@Slf4j
public class TimerDemo {

    public static void main(String[] args) throws InterruptedException {
//        demo01();
        demo02();
    }

    public static void demo01() throws InterruptedException {
        Timer timer = new Timer();
        log.info("=============第一次启动定时器=============");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("一次性执行，启动定时器延迟3秒执行");
            }
        }, 3000);

        Thread.sleep(5000);
        log.info("=============第二次启动定时器=============");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    log.info("周期性执行，启动定时器延迟3秒执行，之后每隔2秒执行一次，但如果任务时间超过2秒则以任务时间为间隔");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 3000, 2000);
    }

    public static void demo02() {
        Timer timer = new Timer();
        log.info("=============开始启动定时器=============");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    log.info("周期性执行，启动定时器延迟3秒执行，之后每隔2秒执行一次，但如果任务时间超过2秒则以任务时间为间隔");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 3000, 2000);
    }

}
