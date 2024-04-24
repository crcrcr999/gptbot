package com.crcrcr999.gptbot.api.domain.zsxq.service;

import com.alibaba.fastjson.JSON;
import com.crcrcr999.gptbot.api.domain.zsxq.IZsxqApi;
import com.crcrcr999.gptbot.api.domain.zsxq.model.aggregates.CommentsAggregates;
import com.crcrcr999.gptbot.api.domain.zsxq.model.req.CommentReq_data;
import com.crcrcr999.gptbot.api.domain.zsxq.model.req.Req_data;
import com.crcrcr999.gptbot.api.domain.zsxq.model.res.CommentRes;
import net.sf.json.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author:水门土一
 * @Created:2024/4/17-16:41
 * @Description：api实现类
 */
@Service
public class ZsxqApi implements IZsxqApi {

    private Logger logger = LoggerFactory.getLogger(ZsxqApi.class);
    @Override
    public CommentsAggregates queryComment(String groupId, String cookie) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/"+groupId+"/topics?scope=all&count=20");
        get.addHeader("cookie",cookie);
        get.addHeader("Content-Type","application/json;charset=utf8");
        get.addHeader("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");
        CloseableHttpResponse response = httpClient.execute(get);
        if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
            String jsonstr = EntityUtils.toString(response.getEntity());
//            logger.info("拉取数据，groupId={},jsonstr={}",groupId,jsonstr);
            return JSON.parseObject(jsonstr,CommentsAggregates.class);
        }else{
            throw new RuntimeException("queryComments Err code is "+response.getStatusLine().getStatusCode());
        }
    }

    @Override
    public boolean answer( String topicId, String text, String cookie) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/"+topicId+"/comments");
        post.addHeader("Content-Type","application/json;charset=utf8");
        post.addHeader("cookie",cookie);
        post.addHeader("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");

        CommentReq_data req = new CommentReq_data(new Req_data(text));
        String jsonString = JSONObject.fromObject(req).toString();
        StringEntity json = new StringEntity(jsonString, ContentType.create("text/json","UTF-8"));
        post.setEntity(json);
        CloseableHttpResponse response = httpClient.execute(post);
        if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("评论结果，topicid={},jsonstr={}",topicId,jsonStr);
            CommentRes commentRes = JSON.parseObject(jsonStr,CommentRes.class);
            return commentRes.isSucceeded();
        }else{
            throw new RuntimeException("comment err code is "+response.getStatusLine().getStatusCode());
        }

    }
}
