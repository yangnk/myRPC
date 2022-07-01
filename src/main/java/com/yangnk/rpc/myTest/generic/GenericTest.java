package com.yangnk.rpc.myTest.generic;

/**
 * @author yangningkai
 * @create 2022-06-30 16:56
 **/

public class GenericTest {

    static <T> T set(T t) {
        System.out.println(t.toString());
        return t;
    }

    public static void main(String[] args) {
        String hello = set("hello");
        Integer set = set(123);
    }
}
