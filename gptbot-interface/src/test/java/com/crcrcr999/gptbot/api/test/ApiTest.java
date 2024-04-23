package com.crcrcr999.gptbot.api.test;

import com.crcrcr999.gptbot.api.domain.ai.service.OpenAI;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * @Author:水门土一
 * @Created:2024/4/16 - 18:29
 * @Description： 单元测试
 */
public class ApiTest {

    @Test
    public void query_topics() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/28885518425541/topics?scope=all&count=20");
        get.addHeader("cookie", "zsxq_access_token=33CF4A46-5CCE-F50B-8D90-98D3EEFB688B_29B462082F5E6713; zsxqsessionid=37f3a5c0b8c779137b593b566a191dc4; abtest_env=product; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2215551211544512%22%2C%22first_id%22%3A%2218ee57bdb9f8d-0a68dbb07efb4b8-1c525637-1764000-18ee57bdba09e7%22%2C%22props%22%3A%7B%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThlZTU3YmRiOWY4ZC0wYTY4ZGJiMDdlZmI0YjgtMWM1MjU2MzctMTc2NDAwMC0xOGVlNTdiZGJhMDllNyIsIiRpZGVudGl0eV9sb2dpbl9pZCI6IjE1NTUxMjExNTQ0NTEyIn0%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%2215551211544512%22%7D%2C%22%24device_id%22%3A%2218ee57bdb9f8d-0a68dbb07efb4b8-1c525637-1764000-18ee57bdba09e7%22%7D");
        get.addHeader("Content-Type", "application/json;charset=utf8");
        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }

    }

    @Test
    public void answer() throws IOException {
        //{"req_data":{"text":"托尔斯泰\n","image_ids":[],"mentioned_user_ids":[]}}
        //https://api.zsxq.com/v2/topics/2855288542585841/comments
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/2855288542585841/comments");
        post.addHeader("Content-Type", "application/json;charset=utf8");
        post.addHeader("cookie", "zsxq_access_token=33CF4A46-5CCE-F50B-8D90-98D3EEFB688B_29B462082F5E6713; zsxqsessionid=37f3a5c0b8c779137b593b566a191dc4; abtest_env=product; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2215551211544512%22%2C%22first_id%22%3A%2218ee57bdb9f8d-0a68dbb07efb4b8-1c525637-1764000-18ee57bdba09e7%22%2C%22props%22%3A%7B%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThlZTU3YmRiOWY4ZC0wYTY4ZGJiMDdlZmI0YjgtMWM1MjU2MzctMTc2NDAwMC0xOGVlNTdiZGJhMDllNyIsIiRpZGVudGl0eV9sb2dpbl9pZCI6IjE1NTUxMjExNTQ0NTEyIn0%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%2215551211544512%22%7D%2C%22%24device_id%22%3A%2218ee57bdb9f8d-0a68dbb07efb4b8-1c525637-1764000-18ee57bdba09e7%22%7D");
        String paramjson = "{\n" +
                "    \"req_data\": {\n" +
                "        \"text\": \"123\\n\",\n" +
                "        \"image_ids\": [],\n" +
                "        \"mentioned_user_ids\": []\n" +
                "    }\n" +
                "}";
        StringEntity json = new StringEntity(paramjson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(json);
        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void test_chatgpt() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer xxx");
        String paramjson = "{ \"model\": \"gpt-4-turbo\", \"messages\": [ { \"role\": \"system\", \"content\": " +
                "\"你是一个编码高手，用最专业的代码回答用户提问。\"" +
                " }," +
                " {" +
                " " +
                "\"role\": \"user\", \"content\": \"你的语言模型是什么？\" } ] }";
        StringEntity json = new StringEntity(paramjson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(json);
        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }
    }
}
