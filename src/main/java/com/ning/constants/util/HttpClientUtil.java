package com.ning.constants.util;

import com.ning.constants.config.HttpHeaderConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @Author: nicholas
 * @Date: 2020/9/20 14:03
 * @Descreption:
 */
@Slf4j
public class HttpClientUtil {

    public static String sendGetRequest(String url) {
        CloseableHttpClient defaultHttpClient = HttpClients.createDefault();
        HttpGet hg = new HttpGet("https://dreamtowntz.58.com" + url);
        setHttpHeader(hg, HttpHeaderConfig.GUOTONGNING);
        try {
            HttpResponse response = defaultHttpClient.execute(hg);
            String entityStringBuilder = getResponseJson(response);
            if (entityStringBuilder != null) return entityStringBuilder;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }

    public static String sendPostRequest(String url, String param) {
        CloseableHttpClient defaultHttpClient = HttpClients.createDefault();
        HttpPost hp = new HttpPost("https://dreamtowntz.58.com" + url + "?" + param);
        setHttpHeader(hp, HttpHeaderConfig.GUOTONGNING);
        try {
            HttpResponse response = defaultHttpClient.execute(hp);
            String entityStringBuilder = getResponseJson(response);
            if (entityStringBuilder != null) return entityStringBuilder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static void setHttpHeader(HttpRequestBase hp, Map<String, String> userConfig) {
        for (Map.Entry<String, String> entry : userConfig.entrySet()) {
            hp.setHeader(entry.getKey(), entry.getValue());
        }
    }

    private static String getResponseJson(HttpResponse response) throws IOException {
        BufferedReader bufferedReader;
        StringBuilder entityStringBuilder = new StringBuilder();
        //得到httpResponse的状态响应码
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            //得到httpResponse的实体数据
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                bufferedReader = new BufferedReader
                        (new InputStreamReader(httpEntity.getContent(), "UTF-8"), 8 * 1024);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    entityStringBuilder.append(line);
                }
                return entityStringBuilder.toString();
            }
        }
        return null;
    }
}
