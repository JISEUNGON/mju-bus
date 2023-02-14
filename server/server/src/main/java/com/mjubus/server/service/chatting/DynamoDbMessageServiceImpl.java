package com.mjubus.server.service.chatting;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.mjubus.server.vo.ChattingMessage;
import com.mjubus.server.vo.MessageHistory;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<MessageHistory> findMessageHistory(Long id) {
        MessageHistory target = MessageHistory.builder()
                .partyId(id)
                .build();

        DynamoDBQueryExpression<MessageHistory> queryExpression = new DynamoDBQueryExpression<MessageHistory>()
                .withHashKeyValues(target);

        List<MessageHistory> itemList = dynamoDBMapper.query(MessageHistory.class, queryExpression);

        return itemList;
    }

}
