package com.yangnk.others.myTest.JdkDynastyProxy;

import java.lang.reflect.Proxy;

/**
 * @author yangningkai
 * @create 2019-01-05 16:13
 **/

public class Consumer {
    public static void main(String[] args) {
        IUSBFactory usbFactory = new KinstonUSBFactory();
        USBInvocationHandler usbInvocationHandler = new USBInvocationHandler(usbFactory);
        IUSBFactory instance = (IUSBFactory)Proxy.newProxyInstance(usbFactory.getClass().getClassLoader(), usbFactory.getClass().getInterfaces(), usbInvocationHandler);
        int count = instance.sell(1);
        System.out.printf("=====Costomer count:%d=====\n",count);
    }
}
