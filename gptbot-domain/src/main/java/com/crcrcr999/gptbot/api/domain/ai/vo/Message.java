package com.crcrcr999.gptbot.api.domain.ai.vo;

/**
 * @Author:水门土一
 * @Created:2024/4/23-17:38
 * @Description：
 */
public class Message {
    private String role;

    private String content;

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }
}
