package com.yangnk.others.nettyDemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author yangningkai
 * @create 2019-01-08 23:04
 **/

public class Server {
    public static void main(String[] args) {
        //创建boss、worker，创建bootstrap启动类
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker);
        //设置参数
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        bootstrap.channel(NioServerSocketChannel.class);
        //传入handler进行处理
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                //注册handler
                ch.pipeline().addLast(new MyHandler());
            }
        });
        try {
            ChannelFuture future = bootstrap.bind(9000).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    private static class MyHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            //收到客户端请求的处理
            System.out.println("server handler start...");
            ByteBuf result = (ByteBuf) msg;
            byte[] result1 = new byte[result.readableBytes()];
            result.readBytes(result1);
            String str = new String(result1);
            System.out.println("client say:" + str);
            result.release();
            String response = "=====hello client=====";
            ByteBuf buf = ctx.alloc().buffer(response.length() * 4);
            buf.writeBytes(response.getBytes());
            ctx.write(buf);
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.close();
        }

    }
}
