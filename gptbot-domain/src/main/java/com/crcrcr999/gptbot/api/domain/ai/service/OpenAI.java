package com.crcrcr999.gptbot.api.domain.ai.service;

import com.alibaba.fastjson.JSON;
import com.crcrcr999.gptbot.api.domain.ai.IOpenAI;
import com.crcrcr999.gptbot.api.domain.ai.aggregates.AIAnswer;
import com.crcrcr999.gptbot.api.domain.ai.vo.Choices;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
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
import java.util.List;

/**
 * @Author:水门土一
 * @Created:2024/4/23-17:25
 * @Description：
 */
@Service
public class OpenAI implements IOpenAI {

    private Logger logger = LoggerFactory.getLogger(OpenAI.class);

    @Override
    public String doChatGPT(String openAiKey, String question) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 代理地址；open.aiproxy.xyz、open2.aiproxy.xyz
        HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer " + openAiKey);

        String paramJson = "{\n" +
                "    \"model\": \"gpt-3.5-turbo\",\n" +
                "    \"messages\": [\n" +
                "      {\n" +
                "        \"role\": \"system\",\n" +
                "        \"content\": \"You are a poetic assistant, skilled in explaining complex programming concepts with creative flair.\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"role\": \"user\",\n" +
                "        \"content\": \"" + question + "\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            AIAnswer aiAnswer = JSON.parseObject(jsonStr, AIAnswer.class);
            StringBuilder answers = new StringBuilder();
            List<Choices> choices = aiAnswer.getChoices();
            for (Choices choice : choices) {
                answers.append(choice.getMessage().getContent());
            }
            return answers.toString();
        } else {
            logger.error(EntityUtils.toString(response.getEntity()));
            throw new RuntimeException("api.openai.com Err Code is " + response.getStatusLine().getStatusCode());
        }
    }
}
