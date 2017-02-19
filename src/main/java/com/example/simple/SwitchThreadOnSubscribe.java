package com.example.simple;

/**
 * 将监听过程的执行放入工作线程
 */

public class SwitchThreadOnSubscribe implements OnSubscribe {

    OnSubscribe onSubscribe;
    Scheduler scheduler;

    public SwitchThreadOnSubscribe(Scheduler scheduler, OnSubscribe onSubscribe){
        this.onSubscribe = onSubscribe;
        this.scheduler = scheduler;
    }

    @Override
    public void call(final Observer observer) {
//        if(threadType == Observable.THREAD_MAIN){
//            Observer observer1 = new MainThreadOperator().call(observer);
//            onSubscribe.call(observer1);
//        }else {
//            Thread workThread = new Thread(){
//                @Override
//                public void run() {
//                    onSubscribe.call(observer);
//                }
//            };
//            workThread.setName("work-thread");
//            workThread.start();
//        }

        scheduler.schedule(observer,onSubscribe);

    }
}
