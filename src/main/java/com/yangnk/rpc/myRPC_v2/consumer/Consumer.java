package com.yangnk.rpc.myRPC_v2.consumer;


import com.yangnk.rpc.myRPC_v2.consumer.proxy.RPCProxy;
import com.yangnk.rpc.myRPC_v2.provider.HelloService;

/**
 * @author yangningkai
 * @create 2019-01-10 16:45
 **/

public class Consumer {
    public static void main(String[] args) {
        HelloService helloService = (HelloService) RPCProxy.create(HelloService.class);
        String s = helloService.hello("tom");
        System.out.printf("=====%s=====\n", s);
    }
}
