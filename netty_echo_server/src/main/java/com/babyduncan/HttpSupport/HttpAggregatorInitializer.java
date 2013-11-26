package com.babyduncan.HttpSupport;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * User: guohaozhao (guohaozhao116008@sohu-inc.com)
 * Date: 11/26/13 18:39
 * for netty http request support .
 */
public class HttpAggregatorInitializer extends ChannelInitializer<Channel> {

    private final boolean client;

    public HttpAggregatorInitializer(boolean client) {
        this.client = client;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (client) {
            pipeline.addLast("codec", new HttpClientCodec());
        } else {
            pipeline.addLast("codec", new HttpServerCodec());
        }
        pipeline.addLast("aggegator",
                new HttpObjectAggregator(512 * 1024));
        // Add HttpObjectAggregator to the ChannelPipeline, using a max message size of 512kb.
        // After the message is getting bigger a TooLongFrameException is thrown.

    }
}
