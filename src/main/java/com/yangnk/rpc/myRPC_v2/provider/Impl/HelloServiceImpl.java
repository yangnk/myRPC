package com.yangnk.rpc.myRPC_v2.provider.Impl;

import com.yangnk.rpc.myRPC_v2.provider.HelloService;

/**
 * @author yangningkai
 * @create 2019-01-10 10:10
 **/

public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "hello " + name;
    }
}
