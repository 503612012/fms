package com.oven.fms.core.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * 异步记录日志线程
 *
 * @author Oven
 */
@Slf4j
public class ExecLogThread implements Runnable {

    @Override
    public void run() { // 这里如果不想用while true的方式，可将生产的消息放入消息中间件中，例如kafka，然后这里开启一个监听，消费消息即可。
//        String info;
//        while (true) {
//            try {
//                info = QueueUtils.getInstance().poll();
//                log.info("从队列中获取到的信息：{}", info);
//                // do somthing
//            } catch (Exception e) {
//                log.error("异步记录日志线程异常，异常信息：", e);
//            }
//        }
    }

}
