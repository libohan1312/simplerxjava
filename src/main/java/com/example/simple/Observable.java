package com.example.simple;

/**
 * 最重要的类，总的调度执行，也是对外接口
 */

public class Observable<T> {
    OnSubscribe<T> onSubscribe;
    boolean observeOnWorkThread = false;

    private Observable(OnSubscribe<T> onSubscribe){
        this.onSubscribe = onSubscribe;
    }

    public static <T> Observable<T> create(OnSubscribe<T> onSubscribe){
        return new Observable<T>(onSubscribe);
    }

    //转换操作符就是重新生成一个Observable，而新的Observable无非是有一个新的OnSubscribe
    public <R> Observable<R> translate(Operator<R,T> operator){
        return create(new TranslateOnSubscribe<R,T>(onSubscribe,operator));
    }

    //线程切换操作符思路是相同的，即重新生成Observable
    public Observable<T> subscribeOnMainThread(){
        return create(new SwitchThreadOnSubscribe(new MainThreadOperator(),onSubscribe));
    }


    public Observable<T> observeOnWorkThread(){
        observeOnWorkThread = true;
    return this;

    }

    public void subscribe(final Observer<T> observer){
        //这里是执行调用的最外层，无论之前经过多少次操作符转换，都是从这里开始的，如果把这里的执行放入一个线程，那之后的调用就都在这个线程了
        if(observeOnWorkThread){
            runOnWorkThread(observer);
        }else {
            onSubscribe.call(observer);
        }

    }

    private void runOnWorkThread(final Observer<T> observer){
        Thread workThread = new Thread(){
            @Override
            public void run() {
                onSubscribe.call(observer);
            }
        };
        workThread.setName("thread-work");
        workThread.start();
    }
}
