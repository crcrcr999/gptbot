package com.crcrcr999.gptbot.api.domain.zsxq;

import com.crcrcr999.gptbot.api.domain.zsxq.model.aggregates.CommentsAggregates;

import java.io.IOException;

/**
 * @Author:水门土一
 * @Created:2024/4/17-16:12
 * @Description：知识星球api接口
 */
public interface IZsxqApi {

    CommentsAggregates queryComment(String groupId, String cookie) throws IOException;
    boolean answer(String topicId,String text,String cookie) throws IOException;
}
