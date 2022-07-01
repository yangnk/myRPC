package com.yangnk.rpc.myRPC.consumer;

import com.yangnk.rpc.myRPC.provider.HelloService;
import com.yangnk.rpc.myRPC.provider.impl.HelloServiceImpl;
import com.yangnk.rpc.myRPC.registry.Registry;
import com.yangnk.rpc.myRPC.registry.impl.RegistryImpl;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author yangningkai
 * @create 2022-07-01 17:28
 **/

public class Consumer {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Registry registry = new RegistryImpl(8080);
                    registry.register(HelloService.class,HelloServiceImpl.class);
                    registry.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        HelloService helloService = RPCClient.getRemoteProxy(HelloService.class, new InetSocketAddress(8080));
        String result = helloService.sayHello("tom");
        System.out.printf("HelloService.sayHello: %s\n", result);

    }
}
