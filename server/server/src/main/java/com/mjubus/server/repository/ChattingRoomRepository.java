package com.mjubus.server.repository;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ChattingRoomRepository {
    private Map<String, ChannelTopic> topics;

    public ChattingRoomRepository() {
        this.topics = new HashMap<>();
    }

    public void newChattingRoom(String roomId, ChannelTopic topic) {
        topics.put(roomId, topic);
    }

    public Optional<ChannelTopic> getTopic(String roomId) {
        return Optional.ofNullable(topics.get(roomId));
    }
}
