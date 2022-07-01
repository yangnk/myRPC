package com.yangnk.rpc.myRPC.registry.impl;

import com.yangnk.rpc.myRPC.registry.Registry;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yangningkai
 * @create 2022-07-01 15:40
 **/

public class RegistryImpl implements Registry {
    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    static final Map<String, Class> registryMap = new HashMap<>();
    static boolean isRunning = false;
    int port;

    public RegistryImpl(int port) throws IOException {
        this.port = port;
    }

    @Override
    public void stop() {
        isRunning = false;
        executorService.shutdown();
    }

    @Override
    public void start() throws IOException {
        isRunning = true;
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress( port));
        while (true) {
            executorService.execute(new serverTask(serverSocket.accept()));
        }
    }

    @Override
    public void register(Class ClazzInterface, Class clazz) {
        registryMap.put(ClazzInterface.getName(), clazz);
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public int getPort() {
        return port;
    }

    private static class serverTask implements Runnable {
        Socket client = new Socket();

        public serverTask(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            ObjectOutputStream objectOutputStream = null;
            ObjectInputStream objectInputStream = null;
            try {
                //从socket中读取ObjectInputStream，将远程调用后的数据写到ObjectOutputStream中
                objectInputStream = new ObjectInputStream(client.getInputStream());
                String serviceName = objectInputStream.readUTF();
                String methodName = objectInputStream.readUTF();

                Object[] arguments = (Object[]) objectInputStream.readObject();
                Class<?>[] parameterTypes = (Class<?>[]) objectInputStream.readObject();

                Class serviceClass = registryMap.get(serviceName);
                if (serviceClass == null) {
                    throw new ClassNotFoundException(serviceName + "not fount.");
                }

                Method method = serviceClass.getMethod(methodName, parameterTypes);
                Object result = method.invoke(serviceClass.newInstance(), arguments);

                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                objectOutputStream.writeObject(result);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    objectInputStream.close();
                    objectOutputStream.close();
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
