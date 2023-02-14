package com.mjubus.server.service.chatting;

import com.mjubus.server.vo.ChattingMessage;
import com.mjubus.server.vo.MessageHistory;

import java.util.List;

public interface DynamoDbMessageService {
    public void saveMessage(ChattingMessage chattingMessage);
    public List<MessageHistory> findMessageHistory(Long id);
}
