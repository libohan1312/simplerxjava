package com.example.simple;

/**
 * Created by Administrator on 2017/2/19.
 */

public class Schedulers {

    public static Scheduler obmain(){
        return new Scheduler() {
            @Override
            public void schedule(Observer observer,OnSubscribe onSubscribe) {
                Observer observer1 = new MainThreadOperator().call(observer);
                onSubscribe.call(observer1);
            }
        };
    }

    public static Scheduler obwork(){
        return new Scheduler() {
            @Override
            public void schedule(Observer observer, OnSubscribe onSubscribe) {
                Observer observer1 = new WorkThreadOperator().call(observer);
                onSubscribe.call(observer1);
            }
        };
    }

    public static Scheduler subMain(){
        return new Scheduler() {
            @Override
            public void schedule(final Observer observer, final OnSubscribe onSubscribe) {
                Thread workThread = new Thread(){
                    @Override
                    public void run() {
                        onSubscribe.call(observer);
                    }
                };
                workThread.setName("main-thread");
                workThread.start();
            }
        };
    }

    public static Scheduler subWork(){
        return new Scheduler() {
            @Override
            public void schedule(final Observer observer, final OnSubscribe onSubscribe) {
                Thread workThread = new Thread(){
                    @Override
                    public void run() {
                        onSubscribe.call(observer);
                    }
                };
                workThread.setName("work-thread");
                workThread.start();
            }
        };
    }

}
