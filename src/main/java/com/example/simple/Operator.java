package com.example.simple;

/**
 * Created by qinfeng on 2016/12/29.
 */

interface Operator<R,T> {
    Observer<T> call(Observer<R> r);
}
