package com.babyduncan;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MixedAttribute;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;

/**
 * User: guohaozhao (guohaozhao116008@sohu-inc.com)
 * Date: 11/27/13 10:33
 */

public class HttpHandler<T extends NettyHttpHandler> extends ChannelInboundHandlerAdapter {

    private Map<String, T> urlMapping;

    public HttpHandler(Map<String, T> urlMapping) {
        this.urlMapping = urlMapping;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            String result = "";
            HttpRequest req = (HttpRequest) msg;
            System.out.println("request url is " + req.getUri());
            String mappingUri = req.getUri();
            if (mappingUri.contains("?")) {
                mappingUri = mappingUri.substring(0, mappingUri.indexOf("?"));
            }
            NettyHttpHandler nettyHttpHandler = urlMapping.get(mappingUri);

            Map<String, String> requestParameters = getRequestParameters(req);
            if (nettyHttpHandler == null) {
                result = "no mapping uri handler";
            } else {
                result = nettyHttpHandler.handle(requestParameters);
            }

            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(result.getBytes()));
            response.headers().set(CONTENT_TYPE, "text/plain");
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());

            ctx.write(response).addListener(ChannelFutureListener.CLOSE);

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private Map<String, String> getRequestParameters(HttpRequest httpRequest) throws IOException {

        Map<String, String> parameters = new HashMap<String, String>();
        if (httpRequest == null) {
            return parameters;
        }
        if (httpRequest.getMethod().equals(HttpMethod.GET)) {
            QueryStringDecoder decoderQuery = new QueryStringDecoder(httpRequest.getUri());
            for (Map.Entry<String, List<String>> entry : decoderQuery.parameters().entrySet()) {
                parameters.put(entry.getKey(), entry.getValue().get(0));
            }
            return parameters;
        } else if (httpRequest.getMethod().equals(HttpMethod.POST)) {
            HttpPostRequestDecoder httpPostRequestDecoder = new HttpPostRequestDecoder(httpRequest);
            List<InterfaceHttpData> interfaceHttpDatas = httpPostRequestDecoder.getBodyHttpDatas();
            for (InterfaceHttpData interfaceHttpData : interfaceHttpDatas) {
                if (interfaceHttpData.getHttpDataType().equals(InterfaceHttpData.HttpDataType.Attribute)) {
                    MixedAttribute attribute = (MixedAttribute) interfaceHttpData;
                    parameters.put(interfaceHttpData.getName(), attribute.getValue());
                }
            }
            return parameters;
        } else {
            return parameters;
        }

    }

}
