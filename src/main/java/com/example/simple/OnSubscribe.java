package com.example.simple;

/**
 * Created by qinfeng on 2016/12/29.
 */

interface OnSubscribe<T> {
    void call(Observer<T> observer);
}
