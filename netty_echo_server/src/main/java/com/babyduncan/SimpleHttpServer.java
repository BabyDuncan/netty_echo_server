package com.babyduncan;

import com.babyduncan.internal.SimpleNettyHttpHandler;
import com.babyduncan.restful.RestfulRegistryCenter;

/**
 * User: guohaozhao (guohaozhao116008@sohu-inc.com)
 * Date: 11/27/13 14:59
 */
public class SimpleHttpServer extends HttpServer {

    public SimpleHttpServer(int port) {
        super(port);
    }

    public static void main(String... args) throws Exception {
        RestfulRegistryCenter.addRestfulMapping("/foobar/$name", "/foobar");
        new SimpleHttpServer(8085).register(SimpleNettyHttpHandler.urlMapping, new SimpleNettyHttpHandler()).start();
    }

}
