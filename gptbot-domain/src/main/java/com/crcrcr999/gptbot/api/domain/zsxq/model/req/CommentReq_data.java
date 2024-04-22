package com.crcrcr999.gptbot.api.domain.zsxq.model.req;

/**
 * @Author:水门土一
 * @Created:2024/4/17-17:12
 * @Description：
 */
import java.util.List;

public class CommentReq_data
{
    private Req_data req_data;

    public CommentReq_data(Req_data req_data){
        this.req_data = req_data;
    }

    public void setReq_data(Req_data req_data){
        this.req_data = req_data;
    }
    public Req_data getReq_data(){
        return this.req_data;
    }
}

