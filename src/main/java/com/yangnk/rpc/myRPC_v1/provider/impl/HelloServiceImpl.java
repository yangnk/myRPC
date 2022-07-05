package com.yangnk.rpc.myRPC_v1.provider.impl;

import com.yangnk.rpc.myRPC_v1.provider.HelloService;

/**
 * @author yangningkai
 * @create 2022-07-01 15:37
 **/

public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        String result = "hello, " + name;
        return result;
    }
}
