package com.mjubus.server.vo;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.mjubus.server.config.DynamoDBConfig.LocalDateTimeConverter;
import com.mjubus.server.util.DateHandler;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter // Setters are used in aws-dynamodb-sdk
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "MetaData")
public class MessageHistory {
    @DynamoDBHashKey(attributeName = "party-id")
    private Long partyId;
    @DynamoDBRangeKey(attributeName = "published_at")
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    private LocalDateTime publishedAt;
    @DynamoDBAttribute(attributeName = "user")
    private String user;
    @DynamoDBAttribute(attributeName = "message")
    private String message;
    @DynamoDBAttribute(attributeName = "user-id")
    private Long userId;

    public static MessageHistory of(ChattingMessage chattingMessage) {
        return MessageHistory.builder()
                .partyId(Long.parseLong(chattingMessage.getRoomId()))
                .message(chattingMessage.getMessage())
                .user(chattingMessage.getSender())
                .userId(Long.parseLong(chattingMessage.getMemberId()))
                .publishedAt(DateHandler.getToday())
                .build();
    }
}
