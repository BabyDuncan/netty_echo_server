package com.babyduncan.katas;

/**
 * User: guohaozhao (guohaozhao116008@sohu-inc.com)
 * Date: 11/27/13 15:35
 */
public class TestCutURL {

    public static void main(String... args) {
        String url = "http://www.babyduncan.com?a=b";
        System.out.println(url.substring(0, url.indexOf("?")));
    }

}
