package com.yangnk.rpc.myRPC_v2.consumer.proxy;

import com.yangnk.rpc.myRPC_v2.registry.InvokeProtocal;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author yangningkai
 * @create 2019-01-10 16:08
 **/

public class RPCProxy {
    public static <T> T create(Class<?> clazz) {
        Class<?>[] inter  = clazz.isInterface() ? new Class[]{clazz} : clazz.getInterfaces();

        return (T)Proxy.newProxyInstance(clazz.getClassLoader(), inter, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                InvokeProtocal invoke = new InvokeProtocal();
                invoke.setClassName(clazz.getName());
                invoke.setMethodName(method.getName());
                invoke.setParams(method.getParameterTypes());
                invoke.setValues(args);
                RPCProxyHandler proxyHandler = new RPCProxyHandler();

                EventLoopGroup worker = new NioEventLoopGroup();
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(worker).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) {
                        //接收课客户端请求的处理流程
                        ChannelPipeline pipeline = ch.pipeline();

                        int fieldLength = 4;
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, fieldLength, 0, fieldLength));
                        pipeline.addLast(new LengthFieldPrepender(fieldLength));
                        pipeline.addLast("encoder", new ObjectEncoder());
                        pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                        pipeline.addLast("handler", proxyHandler);
                    }
                }).option(ChannelOption.TCP_NODELAY, true);


                try {
                    ChannelFuture future = bootstrap.connect("127.0.0.1", 9000).sync();
                    future.channel().writeAndFlush(invoke).sync();
                    future.channel().closeFuture().sync();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    worker.shutdownGracefully();
                }
                return proxyHandler.getResponse();
            }
        });
    }
}
