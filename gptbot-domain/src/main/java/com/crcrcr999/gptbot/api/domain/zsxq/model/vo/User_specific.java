package com.crcrcr999.gptbot.api.domain.zsxq.model.vo;

/**
 * @Author:水门土一
 * @Created:2024/4/17-16:19
 * @Description：
 */
public class User_specific
{
    private boolean liked;

    private boolean subscribed;

    public void setLiked(boolean liked){
        this.liked = liked;
    }
    public boolean getLiked(){
        return this.liked;
    }
    public void setSubscribed(boolean subscribed){
        this.subscribed = subscribed;
    }
    public boolean getSubscribed(){
        return this.subscribed;
    }
}
