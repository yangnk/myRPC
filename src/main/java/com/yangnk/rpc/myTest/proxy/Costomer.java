package com.yangnk.rpc.myTest.proxy;

/**
 * @author yangningkai
 * @create 2022-06-30 15:44
 **/

public class Costomer {
    public static void main(String[] args) {
        JDProxy jd = new JDProxy();
        int count = jd.sell(2);
        System.out.printf("=====Costomer count:%d=====\n",count);
    }
}
