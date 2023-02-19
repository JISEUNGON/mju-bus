package com.mjubus.server.comments;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.mjubus.server.vo.MessageHistory;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MessageHistoryConfigurationTest {
    @Autowired
    AmazonDynamoDB amazonDynamoDb;
    @Autowired
    DynamoDBMapper dynamoDbMapper;

    @BeforeEach
    void createTable() {
        CreateTableRequest createTableRequest = dynamoDbMapper.generateCreateTableRequest(MessageHistory.class)
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        TableUtils.createTableIfNotExists(amazonDynamoDb, createTableRequest);
    }

    @Test
    void createComment_ValidInput_CreatedComment() {
        MessageHistory history = MessageHistory.builder()
                .partyId(2L)
                .message("test")
                .publishedAt(LocalDateTime.now())
                .userId(1L)
                .build();

        dynamoDbMapper.save(history);

        DynamoDBQueryExpression<MessageHistory> queryExpression = new DynamoDBQueryExpression<MessageHistory>()
                .withHashKeyValues(history);

        List<MessageHistory> itemList = dynamoDbMapper.query(MessageHistory.class, queryExpression);

        for (int i = 0; i < itemList.size(); i++) {
            System.out.println(itemList.get(i).getPublishedAt());
        }

        assertThat(itemList.size()).isNotEqualTo(1);
    }
}