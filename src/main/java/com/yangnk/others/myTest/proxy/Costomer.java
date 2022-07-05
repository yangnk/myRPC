package com.yangnk.others.myTest.proxy;

/**
 * @author yangningkai
 * @create 2019-01-05 15:44
 **/

public class Costomer {
    public static void main(String[] args) {
        JDProxy jd = new JDProxy();
        int count = jd.sell(2);
        System.out.printf("=====Costomer count:%d=====\n",count);
    }
}
