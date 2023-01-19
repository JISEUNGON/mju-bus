package com.mjubus.server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class ChattingRoomRepository {
    private Map<String, ChannelTopic> topics;

    @Autowired
    public ChattingRoomRepository(Map<String, ChannelTopic> topics) {
        this.topics = topics;
    }

    public void put(String roomId, ChannelTopic topic) {
        topics.put(roomId, topic);
    }

    public Optional<ChannelTopic> getTopic(String roomId) {
        return Optional.ofNullable(topics.get(roomId));
    }
}
