package com.mjubus.server.service.chatting;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.mjubus.server.dto.request.MessageHistoryRequest;
import com.mjubus.server.exception.chatting.RoomIdNotFoundExcption;
import com.mjubus.server.vo.ChattingMessage;
import com.mjubus.server.vo.MessageHistory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DynamoDbMessageServiceImpl implements  DynamoDbMessageService{
    private final DynamoDBMapper dynamoDBMapper;

    public DynamoDbMessageServiceImpl(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    @Override
    public void saveMessage(ChattingMessage chattingMessage) {
        dynamoDBMapper.save(MessageHistory.of(chattingMessage));
    }

    @Override
    public List<MessageHistory> findMessageHistory(MessageHistoryRequest request) {
        MessageHistory target = MessageHistory.builder()
                .partyId(request.getRoomId())
                .build();

        DynamoDBQueryExpression<MessageHistory> queryExpression = new DynamoDBQueryExpression<MessageHistory>()
                .withHashKeyValues(target);

        List<MessageHistory> itemList = dynamoDBMapper.query(MessageHistory.class, queryExpression);

        if(itemList.isEmpty()) {
            throw new RoomIdNotFoundExcption(request.getRoomId().toString());
        }

        return itemList;
    }

}
