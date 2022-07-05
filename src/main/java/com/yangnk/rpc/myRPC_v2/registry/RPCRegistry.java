package com.yangnk.rpc.myRPC_v2.registry;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author yangningkai
 * @create 2019-01-10 10:13
 **/

public class RPCRegistry {
    private int port;

    public RPCRegistry(int port) {
        this.port = port;
    }

    private void start() {
        //创建io轮训线程->配置bootstrap
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker);

        //接受请求->进行处理
        bootstrap.channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) throws Exception {

                int fieldLength = 4;
                ChannelPipeline pipeline = ch.pipeline();

                pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, fieldLength, 0, fieldLength));
                pipeline.addLast(new LengthFieldPrepender(fieldLength));
                pipeline.addLast("encoder",new ObjectEncoder());
                pipeline.addLast("decoder",new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));

                pipeline.addLast(new RPCRegistryHandler());
            }
        }).option(ChannelOption.SO_BACKLOG, 128)
          .childOption(ChannelOption.SO_KEEPALIVE, true);
        ChannelFuture future = null;
        try {
            future = bootstrap.bind(this.port).sync();
            System.out.println("RPCRegistry started at port: " + this.port);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new RPCRegistry(9000).start();
    }
}
