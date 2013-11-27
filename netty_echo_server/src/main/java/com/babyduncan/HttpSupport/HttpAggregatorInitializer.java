package com.babyduncan.HttpSupport;

import com.babyduncan.HttpHandler;
import com.babyduncan.NettyHttpHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.Map;

/**
 * User: guohaozhao (guohaozhao116008@sohu-inc.com)
 * Date: 11/26/13 18:39
 * for netty http request support .
 */
public class HttpAggregatorInitializer<T extends NettyHttpHandler> extends ChannelInitializer<Channel> {

    private Map<String, T> urlMapping;

    public HttpAggregatorInitializer(Map<String, T> urlMapping) {
        this.urlMapping = urlMapping;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();

        p.addLast("codec", new HttpServerCodec());

        p.addLast("aggegator",
                new HttpObjectAggregator(512 * 1024));
        // Add HttpObjectAggregator to the ChannelPipeline, using a max message size of 512kb.
        // After the message is getting bigger a TooLongFrameException is thrown.

        p.addLast("handler", new HttpHandler(urlMapping));
    }

}
