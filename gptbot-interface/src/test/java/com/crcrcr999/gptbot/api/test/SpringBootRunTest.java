package com.crcrcr999.gptbot.api.test;

import com.crcrcr999.gptbot.api.domain.ai.IOpenAI;
import com.crcrcr999.gptbot.api.domain.zsxq.IZsxqApi;
import com.crcrcr999.gptbot.api.domain.zsxq.model.aggregates.CommentsAggregates;
import com.crcrcr999.gptbot.api.domain.zsxq.model.vo.Topics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @Author:水门土一
 * @Created:2024/4/17-17:33
 * @Description：
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRunTest {
    private Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);
    @Value("${gptbot.groupId}")
    private String groupId;
    @Value("${gptbot.topicId}")
    private String topicId;
    @Value("${gptbot.cookie}")
    private String cookie;
    @Resource
    private IZsxqApi zsxqApi;

    @Resource
    private IOpenAI openai;

    @Test
    public void test_zsxqApi() throws IOException {
        CommentsAggregates ca = zsxqApi.queryComment(groupId, cookie);
//       logger.info("测试结果{}", JSON.toJSONString(ca));
        if (ca.getSucceeded()) {
            List<Topics> topics = ca.getResp_data().getTopics();
            for (Topics topic : topics) {
                String topicId = topic.getTopic_id();
                String text = topic.getTalk().getText();
                String name = topic.getTalk().getOwner().getName();
                logger.info("评论id：{},评论内容:{},评论发布者：{}", topicId, text, name);
                if (name.equals("耳东水闰") && topic.getShow_comments() == null) {
                    zsxqApi.answer(topicId, "测试", cookie);
                }
            }
        }
    }

    @Test
    public void test_chat() throws IOException {
        openai.doChatGPT("xxx", "你好", "gpt-4-turbo", "你是一个专业的个人助理,旨在回答一切问题");
    }
}
