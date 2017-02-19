package com.example.simple;


/**
 * 最重要的类，总的调度执行，也是对外接口
 */

public class Observable<T> {
    OnSubscribe<T> onSubscribe;
    boolean observeOnWorkThread = false;

    public static  final  int THREAD_MAIN = 0;
    public static  final  int THREAD_WORK = 1;


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
    public Observable<T> subscribeOnWorkThread(){
        return create(new SwitchThreadOnSubscribe(Schedulers.subWork(),onSubscribe));
    }

    public Observable<T> observerOnWorkThread(){
        return create(new SwitchThreadOnSubscribe(Schedulers.obwork(),onSubscribe));
    }


    public Observable<T> observeOnMainThread(){
        return create(new SwitchThreadOnSubscribe(Schedulers.obmain(),onSubscribe));
    }

    public void subscribe(final Observer<T> observer){
        onSubscribe.call(observer);
    }


}
