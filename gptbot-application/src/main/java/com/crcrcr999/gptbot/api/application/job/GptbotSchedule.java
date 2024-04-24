package com.crcrcr999.gptbot.api.application.job;

import com.crcrcr999.gptbot.api.domain.ai.IOpenAI;
import com.crcrcr999.gptbot.api.domain.zsxq.IZsxqApi;
import com.crcrcr999.gptbot.api.domain.zsxq.model.aggregates.CommentsAggregates;
import com.crcrcr999.gptbot.api.domain.zsxq.model.vo.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:水门土一
 * @Created:2024/4/23-18:18
 * @Description：问题轮循任务
 */
@EnableScheduling
@Configuration
public class GptbotSchedule {
    @Value("${gptbot.groupId}")
    private String groupId;
    @Value("${gptbot.cookie}")
    private String cookie;
    @Value("${gptbot.commentname}")
    private String commentname;
    @Value("${gptbot.apikey}")
    private String apikey;
    @Resource
    private IZsxqApi zsxqApi;
    @Resource
    private IOpenAI openai;
    private Logger logger = LoggerFactory.getLogger(GptbotSchedule.class);

    @Scheduled(cron = "* 0/1 * * * ?")
    public void run() {
        logger.info("任务启用");
        try {
            //1. 检索问题
            //2. 检查id
            //3.回答
            CommentsAggregates ca = zsxqApi.queryComment(groupId, cookie);
            if (ca.getSucceeded()) {
                List<Topics> topics = ca.getResp_data().getTopics();
                for (Topics topic : topics) {
                    String topicId = topic.getTopic_id();
                    String text = topic.getTalk().getText();
                    String name = topic.getTalk().getOwner().getName();
                    logger.info("评论id：{},评论内容:{},评论发布者：{}", topicId, text, name);
                    if (name.equals(commentname) && topic.getShow_comments() == null) {
                        String answer = openai.doChatGPT(apikey, text);
                        zsxqApi.answer(topicId, answer, cookie);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("自动回答问题异常：", e.getMessage());
        }
    }
}
