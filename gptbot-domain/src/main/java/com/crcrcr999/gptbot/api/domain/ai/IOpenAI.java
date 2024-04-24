package com.crcrcr999.gptbot.api.domain.ai;

import java.io.IOException;

/**
 * @Author:水门土一
 * @Created:2024/4/23-17:23
 * @Description：chatgpt api接口
 */
public interface IOpenAI {
    String doChatGPT(String openAiKey, String question, String version, String prompt) throws IOException;
}
