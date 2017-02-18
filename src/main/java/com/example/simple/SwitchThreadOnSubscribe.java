package com.example.simple;

/**
 * 将监听过程的执行放入工作线程
 */

public class SwitchThreadOnSubscribe implements OnSubscribe {

    Operator operator;
    OnSubscribe onSubscribe;

    public SwitchThreadOnSubscribe(Operator operator, OnSubscribe onSubscribe){
        this.operator = operator;
        this.onSubscribe = onSubscribe;
    }

    @Override
    public void call(Observer observer) {
        Observer observer1 = operator.call(observer);
        onSubscribe.call(observer1);
    }
}
