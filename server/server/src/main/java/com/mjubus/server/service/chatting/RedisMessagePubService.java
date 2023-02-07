package com.mjubus.server.service.chatting;

import com.mjubus.server.vo.ChattingMessage;

public interface RedisMessagePubService {
    public void publish(ChattingMessage chattingMessage);
    public void publishForFCM(ChattingMessage chattingMessage);
    public void saveMessage(ChattingMessage chattingMessage);
    public void saveSessionHash(ChattingMessage chattingMessage);
}
