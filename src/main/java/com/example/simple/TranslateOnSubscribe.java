package com.example.simple;

/**
 * Created by qinfeng on 2016/12/29.
 */

public class  TranslateOnSubscribe<R,T> implements OnSubscribe<R>{
    Operator<R,T> operator;
    OnSubscribe<T> parent;

    public TranslateOnSubscribe(OnSubscribe<T> parent,Operator<R,T> operator){
        this.operator = operator;
        this.parent = parent;
    }

    @Override
    public void call(Observer<R> observer) {
        Observer<T> tObserver = operator.call(observer);
        parent.call(tObserver);
    }
}
