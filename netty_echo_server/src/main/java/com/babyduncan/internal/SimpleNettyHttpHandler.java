package com.babyduncan.internal;

import com.babyduncan.NettyHttpHandler;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * User: guohaozhao (guohaozhao116008@sohu-inc.com)
 * Date: 11/27/13 14:31
 * one simple netty http handler
 */

public class SimpleNettyHttpHandler implements NettyHttpHandler {

    private static final Logger logger = Logger.getLogger(SimpleNettyHttpHandler.class);

    public static final String urlMapping = "/foobar";

    @Override
    public String handle(Map<String, String> parameters) {

        System.out.println(parameters);
        return "foobar";
    }

}
