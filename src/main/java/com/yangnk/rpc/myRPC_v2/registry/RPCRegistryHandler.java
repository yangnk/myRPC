package com.yangnk.rpc.myRPC_v2.registry;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册中心接受到数据，进行处理的逻辑
 * @author yangningkai
 * @create 2019-01-10 10:22
 **/

public class RPCRegistryHandler extends ChannelInboundHandlerAdapter {
    //注册中心
    ConcurrentHashMap<String, Class> registryMap = new ConcurrentHashMap();
    //保存类目录
    List<String> list = new ArrayList<>();

    public RPCRegistryHandler() {
        //全局扫描
        scannerClass("com.yangnk.rpc.myRPC_v2.provider.Impl");
        //注入到注册中心
        doRegistry();
    }

    private void doRegistry() {
        if (list == null || list.size() == 0) {
            return;
        }
        for (String item : list) {
            try {
                Class<?> clazz = Class.forName(item);
                Class<?> clazzInter = clazz.getInterfaces()[0];
                try {
                    registryMap.put(clazzInter.getName(), clazz);//为什么要用interface的className，用class的不行吗？
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 将packagePath目录下class都放到list中
     * @param packagePath
     */
    private void scannerClass(String packagePath) {
        URL url = this.getClass().getClassLoader().getResource(packagePath.replaceAll("\\.", "/"));
        File file = new File(url.getFile());
        for (File item : file.listFiles()) {
            if (item.isDirectory()) {
                scannerClass(packagePath + "." + item.getName());
            } else {
                String str = packagePath + "." + item.getName().replace(".class", "").trim();
                list.add(str);
                System.out.printf("=====className:%s =====\n", str);
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        InvokeProtocal invoke = (InvokeProtocal) msg;
        Object obj = new Object();

        //通过反射的方式获取对象
        if (registryMap.containsKey(invoke.getClassName())) {
            Class clazz = registryMap.get(invoke.getClassName());
            Object instance = clazz.newInstance();
            Method method = instance.getClass().getMethod(invoke.getMethodName(), invoke.getParams());
            obj = method.invoke(instance, invoke.getValues());
        }

        ctx.write(obj);
        ctx.flush();
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
