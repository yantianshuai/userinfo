package com.example.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultHttpClientConnectionOperator;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */
public class HttpClientUtil extends DefaultHttpClient{
    public static String httpGet(String url){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response1 = null;
        String jsonStr = null;
        try {
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
            response1 = httpclient.execute(httpGet);
            Header[] allHeaders = response1.getAllHeaders();
            for(Header h:allHeaders){
                System.out.println(h.getName()+":\t"+h.getValue()+"\n");
            }
            System.out.println(response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
            jsonStr = EntityUtils.toString(entity1,"utf-8");
            System.out.println(jsonStr);
            EntityUtils.consume(entity1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                response1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonStr;
    }
    public static String httpPost(String url,String jsonDate) throws UnsupportedEncodingException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String strjson = null;
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> params  = new ArrayList<NameValuePair>();
        if(jsonDate != null){
            HttpEntity param = new StringEntity(jsonDate);
            httpPost.setEntity(param);
            httpPost.setEntity(param);
        }
        try {
            httpPost.setHeader("systemtypeid","1");
            httpPost.setHeader("equipmentnum","1");
            httpPost.setHeader("Content-Type","application/json");
            CloseableHttpResponse response2 = httpclient.execute(httpPost);
            HttpEntity entity = response2.getEntity();
            strjson = EntityUtils.toString(entity);
            System.out.println(strjson);
        } catch (Exception e) {
            e.printStackTrace();
        }
         return strjson;
    }
//    @SuppressWarnings("resource")
//    public static String doPost(String url,String jsonstr,String charset){
//        HttpClient httpClient = null;
//        HttpPost httpPost = null;
//        String result = null;
//        try{
//            httpClient = new SSLClient();
//            httpPost = new HttpPost(url);
//            httpPost.addHeader("Content-Type", "application/json");
//            StringEntity se = new StringEntity(jsonstr);
//            se.setContentType("text/json");
//            se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
//            httpPost.setEntity(se);
//            HttpResponse response = httpClient.execute(httpPost);
//            if(response != null){
//                HttpEntity resEntity = response.getEntity();
//                if(resEntity != null){
//                    result = EntityUtils.toString(resEntity,charset);
//                }
//            }
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
//        return result;
//    }

    public static CloseableHttpClient createSSLClientDefault(){
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                public boolean isTrusted(X509Certificate[] chain,
                                         String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  HttpClients.createDefault();
    }
    public static String httpsGet(String url) throws Exception {
        CloseableHttpClient sslClientDefault = HttpClientUtil.createSSLClientDefault();
        HttpGet get = new HttpGet();
        get.setURI(new URI(url));
        CloseableHttpResponse execute = sslClientDefault.execute(get);
        HttpEntity entity = execute.getEntity();
        String s = EntityUtils.toString(entity, "utf-8");
        return s;

    }

}
