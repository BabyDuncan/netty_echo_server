package com.babyduncan;

import com.babyduncan.HttpSupport.HttpAggregatorInitializer;
import com.babyduncan.internal.SimpleNettyHttpHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.HashMap;
import java.util.Map;

/**
 * User: guohaozhao (guohaozhao116008@sohu-inc.com)
 * Date: 11/27/13 10:33
 */
public class HttpServer<T extends NettyHttpHandler> {
    // listen port
    private int port;
    private Map<String, T> urlMapping = new HashMap<String, T>();

    public HttpServer(int port) {
        this.port = port;
    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public HttpServer register(String s, T t) {
        this.urlMapping.put(s, t);
        return this;
    }

    public void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpAggregatorInitializer(urlMapping));

            Channel ch = b.bind(port).sync().channel();
            System.out.println("http server started on port " + port + " ...");
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println(
                    "Usage: " + HttpServer.class.getSimpleName() + " <port>");
            System.exit(1);
        }
        int port = Integer.parseInt(args[0]);
        new HttpServer(port).register("/foobar", new SimpleNettyHttpHandler()).start();
    }
}
