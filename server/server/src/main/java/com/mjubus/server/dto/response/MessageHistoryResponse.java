package com.mjubus.server.dto.response;

import com.mjubus.server.vo.MessageHistory;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class  MessageHistoryResponse {
    private Long partyId;
    private LocalDateTime publishedAt;
    private String message;
    private Long userId;

    public MessageHistoryResponse(MessageHistory history) {
        this.partyId = history.getPartyId();
        this.publishedAt = history.getPublishedAt();
        this.message = history.getMessage();
        this.userId = history.getUserId();
    }

    public static List<MessageHistoryResponse> of(List<MessageHistory> list) {
        List<MessageHistoryResponse> result = new ArrayList<>();
        list.forEach((i) -> {
            result.add(new MessageHistoryResponse(i));
        });

        return result;
    }
}
