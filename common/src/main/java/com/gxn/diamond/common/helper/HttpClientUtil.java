package com.gxn.diamond.common.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author CHENCHEN
 * @company XJA
 * @create 2019-09-23 16:42
 */
public class HttpClientUtil {

    public static JSONObject doGet(String url){
        JSONObject jsonObject = null;
        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = new DefaultHttpClient();
        jsonObject = getJsonObject(jsonObject, httpGet, httpClient);
        return jsonObject;
    }

    public static JSONObject doPOst(String url, String data){
        JSONObject jsonObject = null;
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        httpPost.setEntity(new StringEntity(data, "utf-8"));
        jsonObject = getJsonObject(jsonObject, httpPost, httpClient);

        return jsonObject;
    }

    private static JSONObject getJsonObject(JSONObject jsonObject, HttpRequestBase httpGet, HttpClient httpClient) {
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode() == 200){
                String str = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                jsonObject = JSON.parseObject(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    public static void main(String[] args) {
        System.out.println(doGet("https://api.weixin.qq.com/sns/jscode2session?appid=wx9222a867ef776cba&secret=2433a35f36518cd5d7007919ce355475&js_code=023wbU1V1cXPwZ0J3q2V1V9R1V1wbU1c&grant_type=authorization_code"));
    }

}