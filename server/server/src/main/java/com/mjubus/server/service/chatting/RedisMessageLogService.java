package com.mjubus.server.service.chatting;


import com.mjubus.server.dto.request.MessageLogRequest;
import com.mjubus.server.dto.response.MessageLogResponse;
import com.mjubus.server.vo.MessageLog;

import java.util.List;

public interface RedisMessageLogService {
    public List<MessageLogResponse> findMessageLog(MessageLogRequest messageLogRequest);
}
