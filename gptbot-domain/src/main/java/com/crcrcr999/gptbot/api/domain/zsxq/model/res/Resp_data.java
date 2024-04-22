package com.crcrcr999.gptbot.api.domain.zsxq.model.res;

/**
 * @Author:水门土一
 * @Created:2024/4/17-16:20
 * @Description：
 */
import com.crcrcr999.gptbot.api.domain.zsxq.model.vo.Topics;

import java.util.List;
public class Resp_data
{
    private List<Topics> topics;

    public void setTopics(List<Topics> topics){
        this.topics = topics;
    }
    public List<Topics> getTopics(){
        return this.topics;
    }
}