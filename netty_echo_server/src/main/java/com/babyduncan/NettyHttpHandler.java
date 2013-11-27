package com.babyduncan;

import java.util.Map;

/**
 * User: guohaozhao (guohaozhao116008@sohu-inc.com)
 * Date: 11/27/13 14:30
 * netty handler interface
 */
public interface NettyHttpHandler {

    public String handle(Map<String, String> parameters);

}
