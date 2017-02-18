package com.example.simple;

import static java.lang.Thread.currentThread;

/**
 * Created by qinfeng on 2016/12/29.
 */

public class SimpleTest {



    public static void main(String[] args){

        Observable.create(new OnSubscribe<String>() {
            @Override
            public void call(final Observer<String> observer) {

                System.out.println("get token from net : thread "+currentThread().getName());
                String token = getString();
                observer.onNext(token);

            }
        }).translate(new Operator<Integer, String>() {
            @Override
            public Observer<String> call(final Observer<Integer> r) {

                return new Observer<String>() {
                    @Override
                    public void onNext(String token) {
                        //get int from net by token
                        System.out.println("get int from net by token : thread "+Thread.currentThread().getName());
                        Integer i = getIntByToken(token);
                        r.onNext(i);
                    }
                };
            }
        }).translate(new Operator<String, Integer>() {
            @Override
            public Observer<Integer> call(final Observer<String> r) {
                return new Observer<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("get int from net by token : thread "+Thread.currentThread().getName());
                        r.onNext(integer.toString());
                    }
                };
            }
        }).subscribeOnMainThread()
        .observeOnWorkThread().
                subscribe(new Observer<String>() {
            @Override
            public void onNext(final String s) {
                System.out.println(s+" thread : "+Thread.currentThread().getName());
            }
        });
    }

    private static int getIntByToken(String token){
        return Integer.parseInt(token);
    }

    private static String getString(){
        return "34";
    }
}
