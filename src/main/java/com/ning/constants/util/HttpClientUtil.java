package com.ning.constants.util;

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
        setHttpHeader(hg);
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
        setHttpHeader(hp);
        try {
            HttpResponse response = defaultHttpClient.execute(hp);
            String entityStringBuilder = getResponseJson(response);
            if (entityStringBuilder != null) return entityStringBuilder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static void setHttpHeader(HttpRequestBase hp) {
        hp.setHeader("Accept-Encoding", "gzip, deflate, br");
        hp.setHeader("Connection", "keep-alive");
        hp.setHeader("Host", "dreamtowntz.58.com");
        hp.setHeader("Cookie", "PPU=\"UID=61843573843204&UN=smuo6selp&TT=00bc626e6aa97395da3d6e1dbc71bf47&PBODY=aXOQJep2dzHM8uXnkjcvOC8-vL5sWSz68Aq15DEDWKZhKStcyfzc4etGB4O4x_KzttshsImyrY1VjGPDhr7Rw0xmYKAPVlm67m0PmpuO6kn2w9BwdS6RuEqvVLpMsNhhzBTfPl5XdMUWqnRh18L5I8dJYnpdPZyfaP0qddai554&VER=1\"; 58cooper=userid=61843573843204&username=smuo6selp; 58uname=smuo6selp; www58com=UserID=61843573843204&UserName=smuo6selp; wmda_session_id_13722015424310=1600571398083-299a062e-e3e0-1c09; 58tj_uuid=7b9a2936-9cc0-4fc9-a42f-d571e22710fc; init_refer=; new_session=1; new_uv=25; qz_gdt=; spm=; utm_source=; xxzl_cid=\"e4411315e2a946faacc1b1a125c55456\"; 58mac=02:00:00:00:00:00; Accept-Encoding=deflate, gzip; brand=Apple; charset=UTF-8; cimei=0f607264fc6318a92b9e13c65db7cd3c; deviceToken=\"be3da38859e179eb89315435f79398fa0b222e10a05905d75d37623923ecacbc\"; imei=0f607264fc6318a92b9e13c65db7cd3c; machine=iPhone12,1; os=ios; osv=14.0; platform=iphone; r=1792_828; ua=iPhone 11_iOS 14.0; uid=61843573843204; uuid=5AC876E4-7EF4-4DBF-9430-354A2743D5C0; wmda_visited_projects=%3B13722015424310; wmda_new_uuid=1; wmda_uuid=7eb1b4f36bf32167890dc1c2bf6f527c; id58=c5/nfF9GFNt3GkxBE4oPAg==");
        hp.setHeader("accept", "application/json, text/plain, */*");
        hp.setHeader("accept-language", "zh-cn");
        hp.setHeader("imei", "0f607264fc6318a92b9e13c65db7cd3c");
        hp.setHeader("referer", "https://dreamtowntz.58.com/web/v/dreamtown?from=58local");
        hp.setHeader("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 wbutown/9.17.0");
        hp.setHeader("openudid", "19ff7e947904dec10b38d71778cfb976b7afeccb");
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
