package com.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.util.HttpClientUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws Exception {
//		String s = HttpClientUtil.httpsGet("https://tianjinfc-t.9h-sports.com/wallpaperinfo/picture-mark/40");
		
		String s = HttpClientUtil.httpGet("https://tianjinfc-t.9h-sports.com/wallpaperinfo/picture-mark/40");
		System.out.println(s);
		HashMap param = new HashMap();
		String jsonString = JSON.toJSONString(param);
		String httpPost = HttpClientUtil.httpPost("http:120.132.61.35:8082/users/login/13651068582/e10adc3949ba59abbe56e057f20f883e", jsonString);
		System.out.println(httpPost);
		SpringApplication.run(Application.class, args);
	}
}
