package com.crcrcr999.gptbot.api.application.job;

import com.crcrcr999.gptbot.api.domain.ai.IOpenAI;
import com.crcrcr999.gptbot.api.domain.zsxq.IZsxqApi;
import com.crcrcr999.gptbot.api.domain.zsxq.model.aggregates.CommentsAggregates;
import com.crcrcr999.gptbot.api.domain.zsxq.model.vo.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:水门土一
 * @Created:2024/4/23-18:18
 * @Description：问题轮循任务
 */
public class GptbotTask implements Runnable {
    private String groupId;

    private String groupName;
    private String cookie;
    private String commentname;
    private String apikey;
    private String gptversion;
    private String prompt;
    @Resource
    private IZsxqApi zsxqApi;
    @Resource
    private IOpenAI openai;
    private Logger logger = LoggerFactory.getLogger(GptbotTask.class);

    public GptbotTask(String groupId, String groupName, String cookie, String commentname, String apikey, String gptversion, String prompt, IZsxqApi zsxqApi, IOpenAI openai) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.cookie = cookie;
        this.commentname = commentname;
        this.apikey = apikey;
        this.gptversion = gptversion;
        this.prompt = prompt;
        this.zsxqApi = zsxqApi;
        this.openai = openai;
    }

    public void run() {
        logger.info(groupName + "任务启用");
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
                    if (commentname == null && topic.getShow_comments() == null) {
                        logger.info("评论id：{},评论内容:{},评论发布者：{}", topicId, text, name);
                        String answer = openai.doChatGPT(apikey, text, gptversion, prompt);
                        zsxqApi.answer(topicId, answer, cookie);
                    } else if (name.equals(commentname) && topic.getShow_comments() == null) {
                        logger.info("评论id：{},评论内容:{},评论发布者：{}", topicId, text, name);
                        String answer = openai.doChatGPT(apikey, text, gptversion, prompt);
                        zsxqApi.answer(topicId, answer, cookie);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("{}自动回答问题异常：{}", groupName, e.getMessage());
        }
    }
}
