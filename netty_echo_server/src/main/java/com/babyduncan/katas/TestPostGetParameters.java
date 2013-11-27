package com.babyduncan.katas;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

/**
 * User: guohaozhao (guohaozhao116008@sohu-inc.com)
 * Date: 11/27/13 16:03
 * test get parameters from a post request
 */
public class TestPostGetParameters {

    public static void main(String... args) throws IOException {
        PostMethod postMethod = new PostMethod("http://localhost:8082/foobar");
        NameValuePair[] data = {new NameValuePair("hello", "world"), new NameValuePair("foo", "bar")};
        // 将表单的值放入postMethod中
        postMethod.setRequestBody(data);
        // 执行postMethod
        HttpClient client = new HttpClient();
        int status = client.executeMethod(postMethod);
        if (status == HttpStatus.SC_OK) {
            System.out.println(postMethod.getResponseBodyAsString());
        } else {
            System.out.println("fail" + status);
        }
    }

}
