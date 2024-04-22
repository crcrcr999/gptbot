package com.crcrcr999.gptbot.api.domain.zsxq.model.vo;

/**
 * @Author:水门土一
 * @Created:2024/4/17-16:17
 * @Description：
 */
public class Talk
{
    private Owner owner;

    private String text;

    public void setOwner(Owner owner){
        this.owner = owner;
    }
    public Owner getOwner(){
        return this.owner;
    }
    public void setText(String text){
        this.text = text;
    }
    public String getText(){
        return this.text;
    }
}
