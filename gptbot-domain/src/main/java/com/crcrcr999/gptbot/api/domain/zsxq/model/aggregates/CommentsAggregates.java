package com.crcrcr999.gptbot.api.domain.zsxq.model.aggregates;

import com.crcrcr999.gptbot.api.domain.zsxq.model.res.Resp_data;

/**
 * @Author:水门土一
 * @Created:2024/4/17-16:38
 * @Description：返回最新的评论聚合
 */
public class CommentsAggregates {
    private boolean succeeded;

    private Resp_data resp_data;

    public void setSucceeded(boolean succeeded){
        this.succeeded = succeeded;
    }
    public boolean getSucceeded(){
        return this.succeeded;
    }
    public void setResp_data(Resp_data resp_data){
        this.resp_data = resp_data;
    }
    public Resp_data getResp_data(){
        return this.resp_data;
    }
}
