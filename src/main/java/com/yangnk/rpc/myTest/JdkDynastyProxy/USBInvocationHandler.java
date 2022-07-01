package com.yangnk.rpc.myTest.JdkDynastyProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author yangningkai
 * @create 2022-06-30 16:08
 **/

public class USBInvocationHandler implements InvocationHandler {
    Object target = new Object();
    int fee = 11;

    public USBInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invoke = method.invoke(target, args);
        int result = fee + (int) invoke;
        System.out.printf("===USBInvocationHandler result:%d===\n",result);
        return result;
    }
}
