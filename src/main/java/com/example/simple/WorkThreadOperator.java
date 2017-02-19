package com.example.simple;

/**
 * Created by Administrator on 2017/2/15.
 */

public class WorkThreadOperator implements Operator {
    @Override
    public Observer call(final Observer r) {

        Observer observer = new Observer() {
            @Override
            public void onNext(final Object o) {
                Thread mainThread = new Thread(){
                    @Override
                    public void run() {
                        r.onNext(o);
                    }
                };
                mainThread.setName("work-thread");
                mainThread.start();
            }
        };

        return observer;
    }
}
