package com.mjubus.server.service.chatting;


import com.mjubus.server.vo.MessageLog;

import java.util.List;

public interface RedisMessageLogService {
    public List<MessageLog> findMessageLog(String roomId);
}
