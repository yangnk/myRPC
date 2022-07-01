package com.yangnk.rpc.myRPC.consumer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author yangningkai
 * @create 2022-07-01 16:38
 **/

public class RPCClient<T> {
    public static <T>T getRemoteProxy(Class<T> clazzInterface, InetSocketAddress addr) {
        return (T)Proxy.newProxyInstance(clazzInterface.getClassLoader(), new Class<?>[]{clazzInterface}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                Object result = null;
                ObjectOutputStream objectOutputStream = null;
                ObjectInputStream objectInputStream = null;
                Socket socket = null;
                try {
                    //根据addr，将client对远程service的调用写入到outputStream中，然后将返回结构写入到inputStream中
                    socket = new Socket();
                    socket.connect(addr);

                    objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeUTF(clazzInterface.getName());
                    objectOutputStream.writeUTF(method.getName());

                    objectOutputStream.writeObject(args);
                    objectOutputStream.writeObject(method.getParameterTypes());
                    objectInputStream = new ObjectInputStream(socket.getInputStream());
                    result = objectInputStream.readObject();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                        objectInputStream.close();
                        objectOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return result;
            }
        });
    }
}
